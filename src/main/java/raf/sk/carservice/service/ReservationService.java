package raf.sk.carservice.service;

import raf.sk.carservice.dto.reservation.ReservationRequestDto;
import raf.sk.carservice.dto.reservation.ReservationResponseDto;

import java.util.List;

public interface ReservationService {
    List<String> findAvailableDatesForCar(Long id);
    ReservationResponseDto findById(Long id);
    String makeReservation(ReservationRequestDto dto);
    String cancelReservationById(Long id);
}
