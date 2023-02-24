package raf.sk.carservice.service;

import org.springframework.data.jpa.domain.Specification;
import raf.sk.carservice.dto.car.CarRequestDto;
import raf.sk.carservice.dto.car.CarResponseDto;
import raf.sk.carservice.model.Car;

import java.util.Date;
import java.util.List;

public interface CarService {
    void addCar(CarRequestDto carRequestDto);
    void deleteCarById(Long id);
    List<CarResponseDto> findCarByBrand(String brand);
    List<CarResponseDto> findCarByModel(String model);
    List<CarResponseDto> findCarByType(String type);
    List<CarResponseDto> findAvailableCarsForDates(Date startDate, Date endDate, Specification<Car> spec);
}
