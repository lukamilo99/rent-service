package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.mapper.CarMapper;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.repository.CarRepository;
import raf.sk.carservice.service.CarService;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImplementation implements CarService {
    private CarRepository carRepository;
    private CarMapper carMapper;
    @Override
    public void addCar(CarCreateDto carCreateDto) {
        Car car = carMapper.carCreateDtoToCar(carCreateDto);
        carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Optional<List<CarPresentDto>> findCarByBrand(String brand) {
        return carRepository.findCarByBrand(brand).map(carMapper::carPresentDtoList);
    }

    @Override
    public Optional<List<CarPresentDto>> findCarByModel(String model) {
        return carRepository.findCarByBrand(model).map(carMapper::carPresentDtoList);
    }

    @Override
    public Optional<List<CarPresentDto>> findCarByType(String type) {
        return carRepository.findCarByBrand(type).map(carMapper::carPresentDtoList);
    }
}
