package raf.sk.carservice.service;

import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import java.util.List;
import java.util.Optional;

public interface CarService {

    void addCar(CarCreateDto carCreateDto);
    void deleteCarById(Long id);
    Optional<List<CarPresentDto>> findCarByBrand(String brand);
    Optional<List<CarPresentDto>> findCarByModel(String model);
    Optional<List<CarPresentDto>> findCarByType(String type);

}
