package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.dto.reservationDto.ReservationCreateDto;
import raf.sk.carservice.security.CheckPrivilege;
import raf.sk.carservice.service.ReservationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;
    @PostMapping("/create")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<String> makeReservation(@RequestHeader("Authorization") String authorization, @RequestBody ReservationCreateDto dto){
        return new ResponseEntity<>(reservationService.makeReservation(dto), HttpStatus.CREATED);
    }

    @GetMapping("/dates")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<List<String>> findAvailableDates(@RequestHeader("Authorization") String authorization, @RequestParam Long carId){
        return new ResponseEntity<>(reservationService.findAvailableDatesForCar(carId), HttpStatus.OK);
    }
    @GetMapping("/cars")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<List<CarPresentDto>> findAvailableCarsForDates(@RequestHeader("Authorization") String authorization, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
        return new ResponseEntity<>(reservationService.findAvailableCarsForDates(startDate, endDate), HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT", "MANAGER"})
    public ResponseEntity<String> cancelReservation(@RequestHeader("Authorization") String authorization, @RequestParam Long reservationId){
        return new ResponseEntity<>(reservationService.cancelReservationById(reservationId), HttpStatus.OK);
    }
}
