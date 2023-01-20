package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.security.CheckPrivilege;
import raf.sk.carservice.service.implementation.CarServiceImplementation;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {
    private CarServiceImplementation carService;
    @PostMapping("/add")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<CarCreateDto> addCar(@RequestHeader("Authorization") String authorization, @RequestBody @Valid CarCreateDto carCreateDto){
        carService.addCar(carCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<CarCreateDto> deleteCarById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/find/brand")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER", "CLIENT"})
    public ResponseEntity<List<CarPresentDto>> findCarByBrand(@RequestHeader("Authorization") String authorization, @RequestParam String brand){
        return new ResponseEntity<>(carService.findCarByBrand(brand), HttpStatus.OK);
    }
    @GetMapping("/find/model")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER", "CLIENT"})
    public ResponseEntity<List<CarPresentDto>> findCarByModel(@RequestHeader("Authorization") String authorization, @RequestParam String model){
        return new ResponseEntity<>(carService.findCarByModel(model), HttpStatus.OK);
    }
    @GetMapping("/find/type")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER", "CLIENT"})
    public ResponseEntity<List<CarPresentDto>> findCarByType(@RequestHeader("Authorization") String authorization, @RequestParam String type){
        return new ResponseEntity<>(carService.findCarByType(type), HttpStatus.OK);
    }
    @GetMapping("/find-available-cars")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<List<CarPresentDto>> findAvailableCarsForDates(@RequestHeader("Authorization") String authorization, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                         @And({@Spec(path = "ownerCompany.name", params = "companyName", spec = Equal.class),
                                                                                 @Spec(path = "ownerCompany.city", params = "city", spec = Equal.class)})
                                                                             Specification<Car> spec){
        return new ResponseEntity<>(carService.findAvailableCarsForDates(startDate, endDate, spec), HttpStatus.OK);
    }
}
