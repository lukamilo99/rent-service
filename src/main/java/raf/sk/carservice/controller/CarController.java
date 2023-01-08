package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.CarCreateDto;
import raf.sk.carservice.dto.CarPresentDto;
import raf.sk.carservice.security.CheckPrivilege;
import raf.sk.carservice.service.implementation.CarServiceImplementation;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {
    private CarServiceImplementation carService;
    @PostMapping("/add")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<CarCreateDto> addCar(@RequestBody @Valid CarCreateDto carCreateDto){
        carService.addCar(carCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<CarCreateDto> deleteCarById(@PathVariable Long id){
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/find/brand/{brand}")
    public Optional<List<CarPresentDto>> findCarByBrand(@PathVariable String brand){
        return carService.findCarByBrand(brand);
    }

    @GetMapping("/find/model/{model}")
    public Optional<List<CarPresentDto>> findCarByModel(@PathVariable String model){
        return carService.findCarByModel(model);
    }

    @GetMapping("/find/type/{type}")
    public ResponseEntity<CarPresentDto> findCarByType(@PathVariable String type){
        carService.findCarByType(type);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}