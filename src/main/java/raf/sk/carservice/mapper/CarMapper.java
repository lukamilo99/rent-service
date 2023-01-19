package raf.sk.carservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.model.Car;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CarMapper {
    public Car carCreateDtoToCar(CarCreateDto carCreateDto){
        Car car = new Car();
        car.setBrand(carCreateDto.getBrand());
        car.setType(carCreateDto.getType());
        car.setModel(carCreateDto.getModel());
        car.setPricePerDay(carCreateDto.getPricePerDay());
        return car;
    }

    public CarPresentDto carToCarPresentDto(Car car){
        CarPresentDto carPresentDtoDto = new CarPresentDto();
        carPresentDtoDto.setId(car.getId());
        carPresentDtoDto.setBrand(car.getBrand());
        carPresentDtoDto.setType(car.getType());
        carPresentDtoDto.setModel(car.getModel());
        carPresentDtoDto.setPricePerDay(car.getPricePerDay());
        carPresentDtoDto.setCity(car.getOwnerCompany().getCity());
        carPresentDtoDto.setRentingCompanyName(car.getOwnerCompany().getName());
        return carPresentDtoDto;
    }

    public List<CarPresentDto> carPresentDtoList(List<Car> carList){
        List<CarPresentDto> carPresentDtoList = new ArrayList<>();
        for(Car car: carList){
            carPresentDtoList.add(carToCarPresentDto(car));
        }
        return carPresentDtoList;
    }
}
