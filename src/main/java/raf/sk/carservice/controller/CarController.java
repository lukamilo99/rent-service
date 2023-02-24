package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.car.CarRequestDto;
import raf.sk.carservice.dto.car.CarResponseDto;
import raf.sk.carservice.model.Car;
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
    public ResponseEntity<CarRequestDto> addCar(@RequestBody @Valid CarRequestDto carRequestDto){
        carService.addCar(carRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CarRequestDto> deleteCarById(@PathVariable Long id){
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/find/brand")
    public ResponseEntity<List<CarResponseDto>> findCarByBrand(@RequestParam String brand){
        return new ResponseEntity<>(carService.findCarByBrand(brand), HttpStatus.OK);
    }
    @GetMapping("/find/model")
    public ResponseEntity<List<CarResponseDto>> findCarByModel(@RequestParam String model){
        return new ResponseEntity<>(carService.findCarByModel(model), HttpStatus.OK);
    }
    @GetMapping("/find/type")
    public ResponseEntity<List<CarResponseDto>> findCarByType(@RequestParam String type){
        return new ResponseEntity<>(carService.findCarByType(type), HttpStatus.OK);
    }
    @GetMapping("/find/available-cars")
    public ResponseEntity<List<CarResponseDto>> findAvailableCarsForDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                                          @And({@Spec(path = "ownerCompany.name", params = "companyName", spec = Equal.class),
                                                                                 @Spec(path = "ownerCompany.city", params = "city", spec = Equal.class)})
                                                                             Specification<Car> spec){
        return new ResponseEntity<>(carService.findAvailableCarsForDates(startDate, endDate, spec), HttpStatus.OK);
    }
}
