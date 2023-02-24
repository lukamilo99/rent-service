package raf.sk.carservice.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.car.CarRequestDto;
import raf.sk.carservice.dto.car.CarResponseDto;
import raf.sk.carservice.model.Car;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CarMapper {
    public Car carCreateDtoToCar(CarRequestDto carRequestDto){
        Car car = new Car();

        car.setBrand(carRequestDto.getBrand());
        car.setType(carRequestDto.getType());
        car.setModel(carRequestDto.getModel());
        car.setPricePerDay(carRequestDto.getPricePerDay());

        return car;
    }

    public CarResponseDto carToCarPresentDto(Car car){
        CarResponseDto carPresentDtoDto = new CarResponseDto();

        carPresentDtoDto.setId(car.getId());
        carPresentDtoDto.setBrand(car.getBrand());
        carPresentDtoDto.setType(car.getType());
        carPresentDtoDto.setModel(car.getModel());
        carPresentDtoDto.setPricePerDay(car.getPricePerDay());
        carPresentDtoDto.setCity(car.getOwnerCompany().getCity());
        carPresentDtoDto.setRentingCompanyName(car.getOwnerCompany().getName());

        return carPresentDtoDto;
    }

    public List<CarResponseDto> carPresentDtoList(List<Car> carList){
        List<CarResponseDto> carPresentDtoList = new ArrayList<>();

        for(Car car: carList){
            carPresentDtoList.add(carToCarPresentDto(car));
        }

        return carPresentDtoList;
    }
}
