package raf.sk.carservice.service;

import org.springframework.data.jpa.domain.Specification;
import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.model.Car;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CarService {
    void addCar(CarCreateDto carCreateDto);
    void deleteCarById(Long id);
    List<CarPresentDto> findCarByBrand(String brand);
    List<CarPresentDto> findCarByModel(String model);
    List<CarPresentDto> findCarByType(String type);
    List<CarPresentDto> findAvailableCarsForDates(Date startDate, Date endDate, Specification<Car> spec);

}
