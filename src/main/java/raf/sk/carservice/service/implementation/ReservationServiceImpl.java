package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import raf.sk.carservice.client.ClientService;
import raf.sk.carservice.dto.user.UserResponseDto;
import raf.sk.carservice.dto.reservation.ReservationRequestDto;
import raf.sk.carservice.dto.reservation.ReservationResponseDto;
import raf.sk.carservice.exception.CarNotFoundException;
import raf.sk.carservice.exception.InvalidReservationPeriodException;
import raf.sk.carservice.exception.ReservationNotFoundException;
import raf.sk.carservice.mapper.ReservationMapper;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.model.Reservation;
import raf.sk.carservice.repository.CarRepository;
import raf.sk.carservice.repository.ReservationRepository;
import raf.sk.carservice.security.model.CustomUserDetails;
import raf.sk.carservice.service.ReservationService;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private CarRepository carRepository;
    private ClientService clientService;
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
    public ReservationResponseDto findById(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        return reservationMapper.toDto(reservation);
    }

    public String cancelReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        int numOfReservationDays = getNumberOfDaysBetween(reservation.getStartDate(), reservation.getEndDate()) * -1;
        clientService.updateUserReservationDays(reservation.getUserId(), numOfReservationDays);

        reservationRepository.deleteById(id);
        return "Reservation canceled successfully";
    }
    @Override
    public String makeReservation(ReservationRequestDto dto) {
        CustomUserDetails userDetails = getUserDetails();
        Long userId = userDetails.getId();

        Reservation reservation = isValidTimeForReservation(dto);
        reservation.setUserId(userId);

        Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new CarNotFoundException("Car not found"));
        reservation.setCar(car);

        BigDecimal carPricePerDay = car.getPricePerDay();
        BigDecimal reservationPrice = getPriceAfterUserDiscount(dto, carPricePerDay);
        reservation.setPrice(reservationPrice);

        reservationRepository.save(reservation);
        clientService.updateUserReservationDays(userId, getNumberOfDaysBetween(dto.getStartDate(), dto.getEndDate()));
        return "Reservation successfully created";
    }

    private Reservation isValidTimeForReservation(ReservationRequestDto dto){
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();

        List<Reservation> reservationList = reservationRepository.findByEndDateIsAfterAndCar_IdOrderByStartDate
                (java.sql.Date.valueOf(LocalDate.now()), dto.getCarId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        for(Reservation reservation: reservationList){

            if(startDate.after(reservation.getStartDate()) && startDate.before(reservation.getEndDate())) {
                throw new InvalidReservationPeriodException("Car is already reserved in given period");
            }
            else if(endDate.after(reservation.getStartDate()) && endDate.before(reservation.getEndDate())){
                throw new InvalidReservationPeriodException("Car is already reserved in given period");
            }
            else if(startDate.after(reservation.getStartDate()) && endDate.before(reservation.getEndDate())){
                throw new InvalidReservationPeriodException("Car is already reserved in given period");
            }
        }
        return reservationMapper.toReservation(dto);
    }

    private List<String> findAvailableDates(List<Reservation> reservationList){
        List<String> resultList = new ArrayList<>();
        int i;

        if(reservationList.size() == 0){
            resultList.add(LocalDate.now() + " - any date");
            return resultList;
        }

        if(reservationList.get(0).getStartDate().after(java.sql.Date.valueOf(LocalDate.now()))){
            resultList.add(LocalDate.now() + " - " + reservationList.get(0).getStartDate());
        }

        for(i = 0; i < reservationList.size() - 1; i++){
            resultList.add(reservationList.get(i).getEndDate() + " - " +
                    reservationList.get(i + 1).getStartDate());
        }
        resultList.add(reservationList.get(i).getEndDate() + " - " + "any date");

        return resultList;
    }



    private BigDecimal getPriceAfterUserDiscount(ReservationRequestDto dto, BigDecimal carPricePerDay){
        CustomUserDetails userDetails = getUserDetails();
        Long userId = userDetails.getId();
        int numOfDaysForReservation = getNumberOfDaysBetween(dto.getStartDate(), dto.getEndDate());

        UserResponseDto userInfo =  clientService.getUserById(userId);
        double discountForUser = Double.valueOf(userInfo.getRank().getDiscount());

        return carPricePerDay.multiply(BigDecimal.valueOf(numOfDaysForReservation)).multiply(BigDecimal.valueOf(1.0 - discountForUser / 100.0));
    }

    private int getNumberOfDaysBetween(Date startDate, Date endDate){
        int numOfDaysForReservation;

        if(startDate.equals(endDate)) numOfDaysForReservation = 1;
        else numOfDaysForReservation = Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();

        return numOfDaysForReservation;
    }

    private CustomUserDetails getUserDetails(){
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
