package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.reservation.ReservationRequestDto;
import raf.sk.carservice.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> makeReservation(@RequestBody ReservationRequestDto reservationInfo){
        return new ResponseEntity<>(reservationService.makeReservation(reservationInfo), HttpStatus.CREATED);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<String>> findAvailableDates(@RequestParam Long carId){
        return new ResponseEntity<>(reservationService.findAvailableDatesForCar(carId), HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam Long reservationId){
        return new ResponseEntity<>(reservationService.cancelReservationById(reservationId), HttpStatus.OK);
    }
}
