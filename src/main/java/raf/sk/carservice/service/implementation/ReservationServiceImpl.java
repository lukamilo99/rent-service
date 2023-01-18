package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import raf.sk.carservice.client.dto.user.UserPresentDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.dto.reservationDto.ReservationCreateDto;
import raf.sk.carservice.dto.reservationDto.ReservationPresentDto;
import raf.sk.carservice.mapper.CarMapper;
import raf.sk.carservice.mapper.ReservationMapper;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.model.Reservation;
import raf.sk.carservice.repository.CarRepository;
import raf.sk.carservice.repository.ReservationRepository;
import raf.sk.carservice.service.ReservationService;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private CarMapper carMapper;
    private CarRepository carRepository;
    private RestTemplate userServiceTemplate;

    @Override
    public List<String> findAvailableDatesForCar(Long id) {
        List<String> resultList = new ArrayList<>();
        Optional<List<Reservation>> reservationList = reservationRepository.findByEndDateIsAfterAndCar_IdOrderByStartDate(java.sql.Date.valueOf(LocalDate.now()), id);

        if(reservationList.isPresent()){
            resultList = findAvailableDates(reservationList.get());
        }
        return resultList;
    }

    @Override
    public List<CarPresentDto> findAvailableCarsForDates(Date startDate, Date endDate) {
        return findAvailableCars(startDate, endDate);
    }

    @Override
    public ReservationPresentDto findById(Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent()){
            return reservationMapper.toDto(reservation.get());
        }
        else{
            System.out.println("There is no reservation with id " + id);
            return new ReservationPresentDto();
        }
    }

    public String cancelReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        int numOfReservationDays;

        if(reservation.isPresent()){
            numOfReservationDays = getNumberOfDaysBetween(reservation.get().getStartDate(), reservation.get().getEndDate());
            numOfReservationDays *= -1;
            updateUserReservationDays(reservation.get().getUserId(), numOfReservationDays);
        }

        reservationRepository.deleteById(id);
        return "Reservation canceled successfully";
    }
    @Override
    public String makeReservation(ReservationCreateDto dto) {
        Reservation reservation = isValidTimeForReservation(dto);
        BigDecimal carPricePerDay = null;
        BigDecimal reservationPrice;

        if(reservation == null) return "Reservation failed, try again";

        Optional<Car> car = carRepository.findById(dto.getCarId());
        if(car.isPresent()){
            reservation.setCar(car.get());
            carPricePerDay = car.get().getPricePerDay();
        }

        reservationPrice = getFinalPrice(dto, carPricePerDay);
        reservation.setPrice(reservationPrice);

        reservationRepository.save(reservation);
        return "Reservation successfully created";
    }

    private Reservation isValidTimeForReservation(ReservationCreateDto dto){
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();

        Optional<List<Reservation>> reservationList = reservationRepository.findByEndDateIsAfterAndCar_IdOrderByStartDate
                (java.sql.Date.valueOf(LocalDate.now()), dto.getCarId());

        if(reservationList.isPresent()){

            List<Reservation> finalReservationList = reservationList.get();

            for(Reservation reservation: finalReservationList){
                if(startDate.after(reservation.getStartDate()) && startDate.before(reservation.getEndDate())){
                    return null;
                }
                else if(endDate.after(reservation.getStartDate()) && endDate.before(reservation.getEndDate())){
                    return null;
                }
                else if(startDate.after(reservation.getStartDate()) && endDate.before(reservation.getEndDate())){
                    return null;
                }
            }
        }
        return reservationMapper.toReservation(dto);
    }

    private List<CarPresentDto> findAvailableCars(Date startDate, Date endDate){
        Optional<List<Reservation>> reservationList = reservationRepository.findByEndDateIsAfterAndStartDateIsBeforeOrderByStartDate
                (startDate, endDate);
        List<Car> carList = carRepository.findAll();

        if(reservationList.isPresent()){

            List<Reservation> finalReservationList = reservationList.get();

            for(Reservation reservation: finalReservationList){
                carList.removeIf(car -> car.getId() == reservation.getCar().getId());
            }
        }
        return carList.stream().map(carMapper::carToCarPresentDto).collect(Collectors.toList());
    }

    private List<String> findAvailableDates(List<Reservation> reservationList){
        List<String> resultList = new ArrayList<>();
        int i;

        for(i = 0; i < reservationList.size() - 1; i++){
            resultList.add(reservationList.get(i).getEndDate().toString() + " - " +
                    reservationList.get(i + 1).getStartDate().toString());
        }
        resultList.add(reservationList.get(i).getEndDate().toString() + " - " + "any date");

        return resultList;
    }

    private UserPresentDto getUserById(Long id){
        ResponseEntity<UserPresentDto> user = userServiceTemplate.exchange("/user/find/"
                + id, HttpMethod.GET, null, UserPresentDto.class);
        return user.getBody();
    }

    private void updateUserReservationDays(Long id, int numOfDays){
        ResponseEntity<Void> user = userServiceTemplate.exchange("/user/updateReservationDays/{id}"
                  + "?numOfDays={numOfDays}", HttpMethod.PUT, null, Void.class, id, numOfDays);
    }

    private BigDecimal getFinalPrice(ReservationCreateDto dto, BigDecimal carPricePerDay){
        UserPresentDto user = getUserById(dto.getUserId());
        BigDecimal finalPrice;
        int numOfDaysForReservation = getNumberOfDaysBetween(dto.getStartDate(), dto.getEndDate());
        double discountForUser = Double.valueOf(user.getRank().getDiscount());

        finalPrice = carPricePerDay.multiply(BigDecimal.valueOf(numOfDaysForReservation)).multiply(BigDecimal.valueOf(1.0 - discountForUser / 100.0));

        updateUserReservationDays(dto.getUserId(), numOfDaysForReservation);

        return finalPrice;
    }

    private int getNumberOfDaysBetween(Date startDate, Date endDate){
        int numOfDaysForReservation;

        if(startDate.equals(endDate)) numOfDaysForReservation = 1;
        else numOfDaysForReservation = Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();

        return numOfDaysForReservation;
    }
}
