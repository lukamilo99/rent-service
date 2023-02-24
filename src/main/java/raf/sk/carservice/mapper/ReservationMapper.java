package raf.sk.carservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.reservation.ReservationRequestDto;
import raf.sk.carservice.dto.reservation.ReservationResponseDto;
import raf.sk.carservice.model.Reservation;

@Component
public class ReservationMapper {

    public ReservationResponseDto toDto(Reservation reservation){
        ReservationResponseDto dto = new ReservationResponseDto();

        dto.setCarId(reservation.getCar().getId());
        dto.setUserId(reservation.getUserId());
        dto.setEndDate(reservation.getEndDate());
        dto.setStartDate(reservation.getStartDate());

        return dto;
    }

    public Reservation toReservation(ReservationRequestDto dto){
        Reservation reservation = new Reservation();

        reservation.setEndDate(dto.getEndDate());
        reservation.setStartDate(dto.getStartDate());

        return reservation;
    }
}
