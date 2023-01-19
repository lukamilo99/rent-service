package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.carDto.CarCreateDto;
import raf.sk.carservice.dto.carDto.CarPresentDto;
import raf.sk.carservice.mapper.CarMapper;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.model.Reservation;
import raf.sk.carservice.repository.CarRepository;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.repository.ReservationRepository;
import raf.sk.carservice.service.CarService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarServiceImplementation implements CarService {
    private CarRepository carRepository;
    private CarMapper carMapper;
    private ReservationRepository reservationRepository;
    private RentingCompanyRepository companyRepository;
    @Override
    public void addCar(CarCreateDto carCreateDto) {
        Car car = carMapper.carCreateDtoToCar(carCreateDto);
        Optional<RentingCompany> rentingCompany = companyRepository.findById(carCreateDto.getRentingCompanyId());
        rentingCompany.ifPresent(car::setOwnerCompany);

        carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarPresentDto> findCarByBrand(String brand) {
        Optional<List<Car>> carList = carRepository.findCarByBrand(brand);
        List<CarPresentDto> resultList = new ArrayList<>();

        if(carList.isPresent()){
            resultList = carMapper.carPresentDtoList(carList.get());
        }

        return resultList;
    }

    @Override
    public List<CarPresentDto> findCarByModel(String model) {
        Optional<List<Car>> carList = carRepository.findCarByModel(model);
        List<CarPresentDto> resultList = new ArrayList<>();

        if(carList.isPresent()){
            resultList = carMapper.carPresentDtoList(carList.get());
        }

        return resultList;
    }

    @Override
    public List<CarPresentDto> findCarByType(String type) {
        Optional<List<Car>> carList = carRepository.findCarByType(type);
        List<CarPresentDto> resultList = new ArrayList<>();

        if(carList.isPresent()){
            resultList = carMapper.carPresentDtoList(carList.get());
        }

        return resultList;
    }

    @Override
    public List<CarPresentDto> findAvailableCarsForDates(Date startDate, Date endDate) {
        return findAvailableCars(startDate, endDate);
    }

    private List<CarPresentDto> findAvailableCars(Date startDate, Date endDate){
        Optional<List<Reservation>> reservationList = reservationRepository.findByEndDateIsAfterAndStartDateIsBeforeOrderByStartDate
                (startDate, endDate);
        List<Car> carList = carRepository.findAll();

        if(reservationList.isPresent()){

            List<Reservation> finalReservationList = reservationList.get();

            for(Reservation reservation: finalReservationList){
                carList.removeIf(car -> car.getId() == reservation.getCar().getId());
            }
        }
        return carList.stream().map(carMapper::carToCarPresentDto).collect(Collectors.toList());
    }
}
