package raf.sk.carservice.service;

import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.dto.reservationDto.ReservationCreateDto;
import raf.sk.carservice.dto.reservationDto.ReservationPresentDto;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    List<String> findAvailableDatesForCar(Long id);
    List<CarPresentDto> findAvailableCarsForDates(Date startDate, Date endDate);
    ReservationPresentDto findById(Long id);
    String makeReservation(ReservationCreateDto dto);
    String cancelReservationById(Long id);
}
