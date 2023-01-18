package raf.sk.carservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.reservationDto.ReservationCreateDto;
import raf.sk.carservice.dto.reservationDto.ReservationPresentDto;
import raf.sk.carservice.model.Reservation;

@Component
public class ReservationMapper {

    public ReservationPresentDto toDto(Reservation reservation){
        ReservationPresentDto dto = new ReservationPresentDto();
        dto.setCarId(reservation.getCar().getId());
        dto.setUserId(reservation.getUserId());
        dto.setEndDate(reservation.getEndDate());
        dto.setStartDate(reservation.getStartDate());

        return dto;
    }

    public Reservation toReservation(ReservationCreateDto dto){
        Reservation reservation = new Reservation();
        reservation.setUserId(dto.getUserId());
        reservation.setEndDate(dto.getEndDate());
        reservation.setStartDate(dto.getStartDate());

        return reservation;
    }
}
