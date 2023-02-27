package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import raf.sk.carservice.client.ClientService;
import raf.sk.carservice.dto.interservice.UserInfoDto;
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

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private CarRepository carRepository;
    private ClientService clientService;
    private ReservationMapper reservationMapper;
    @Override
    public List<String> findAvailableDatesForCar(Long carId) {

        List<Reservation> reservationList = reservationRepository
                .findByEndDateIsAfterAndCar_IdOrderByStartDate(java.sql.Date.valueOf(LocalDate.now()), carId)
                .orElseThrow(() -> new ReservationNotFoundException("Car available at all dates"));

        return findAvailableDates(reservationList);
    }

    @Override
    public ReservationResponseDto findById(Long id){

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        return reservationMapper.toDto(reservation);
    }

    public String cancelReservationById(Long id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        int numOfReservationDays = getNumberOfDaysBetween(reservation.getStartDate(), reservation.getEndDate()) * -1;

        reservationRepository.deleteById(id);
        clientService.updateUserReservationDays(reservation.getUserId(), numOfReservationDays);

        return "Reservation canceled successfully";
    }
    @Override
    public String makeReservation(ReservationRequestDto reservationInfo) {

        Long userId = getUserId();
        UserInfoDto userInfo = clientService.getUserInfo(userId);

        Reservation reservation = getReservationIfPeriodValid(reservationInfo);
        Car car = carRepository.findById(reservationInfo.getCarId()).orElseThrow(() -> new CarNotFoundException("Car not found"));
        BigDecimal carPricePerDay = car.getPricePerDay();
        BigDecimal reservationPrice = getPriceAfterUserDiscount(userInfo, reservationInfo, carPricePerDay);

        reservation.setUserId(userId);
        reservation.setCar(car);
        reservation.setPrice(reservationPrice);

        reservationRepository.save(reservation);
        clientService.updateUserReservationDays(userId, getNumberOfDaysBetween(reservationInfo.getStartDate(), reservationInfo.getEndDate()));

        return "Reservation successfully created";
    }

    private Reservation getReservationIfPeriodValid(ReservationRequestDto reservationInfo){

        Date startDate = reservationInfo.getStartDate();
        Date endDate = reservationInfo.getEndDate();
        Long carId = reservationInfo.getCarId();

        List<Reservation> reservationList = reservationRepository.findByEndDateIsAfterAndCar_IdOrderByStartDate
                (java.sql.Date.valueOf(LocalDate.now()), carId)
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

        return reservationMapper.toReservation(reservationInfo);
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

    private BigDecimal getPriceAfterUserDiscount(UserInfoDto userInfo, ReservationRequestDto dto, BigDecimal carPricePerDay){

        Integer numOfDaysForReservation = getNumberOfDaysBetween(dto.getStartDate(), dto.getEndDate());
        Double discountForUser = userInfo.getRank().getDiscount();

        return carPricePerDay.multiply(BigDecimal.valueOf(numOfDaysForReservation)).multiply(BigDecimal.valueOf(1.0 - discountForUser / 100.0));
    }

    private Integer getNumberOfDaysBetween(Date startDate, Date endDate){

        Integer numOfDaysForReservation;

        if(startDate.equals(endDate)) numOfDaysForReservation = 1;
        else numOfDaysForReservation = Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();

        return numOfDaysForReservation;
    }

    private Long getUserId(){
        return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
