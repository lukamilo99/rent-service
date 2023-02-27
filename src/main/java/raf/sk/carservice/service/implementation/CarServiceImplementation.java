package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.car.CarRequestDto;
import raf.sk.carservice.dto.car.CarResponseDto;
import raf.sk.carservice.exception.CarNotFoundException;
import raf.sk.carservice.exception.CompanyNotFoundException;
import raf.sk.carservice.mapper.CarMapper;
import raf.sk.carservice.model.Car;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.model.Reservation;
import raf.sk.carservice.repository.CarRepository;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.repository.ReservationRepository;
import raf.sk.carservice.security.model.CustomUserDetails;
import raf.sk.carservice.service.CarService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarServiceImplementation implements CarService {

    private CarRepository carRepository;
    private CarMapper carMapper;
    private ReservationRepository reservationRepository;
    private RentingCompanyRepository companyRepository;

    @Override
    public void addCar(CarRequestDto carRequestDto) {

        CustomUserDetails userDetails = getUserDetails();
        Car car = carMapper.carCreateDtoToCar(carRequestDto);

        RentingCompany rentingCompany = companyRepository.findById(userDetails.getId()).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
        car.setOwnerCompany(rentingCompany);

        carRepository.save(car);
    }

    @Override
    public void deleteCarById(Long id) {

        carRepository.deleteById(id);
    }

    @Override
    public List<CarResponseDto> findCarByBrand(String brand) {

        List<Car> carList = carRepository.findCarByBrand(brand).orElseThrow(() -> new CarNotFoundException("There is no " + brand + " cars"));

        return carMapper.carPresentDtoList(carList);
    }

    @Override
    public List<CarResponseDto> findCarByModel(String model) {

        List<Car> carList = carRepository.findCarByModel(model).orElseThrow(() -> new CarNotFoundException("There is no " + model + " cars"));

        return carMapper.carPresentDtoList(carList);
    }

    @Override
    public List<CarResponseDto> findCarByType(String type) {

        List<Car> carList = carRepository.findCarByType(type).orElseThrow(() -> new CarNotFoundException("There is no " + type + " cars"));

        return carMapper.carPresentDtoList(carList);
    }

    @Override
    public List<CarResponseDto> findAvailableCarsForDates(Date startDate, Date endDate, Specification<Car> spec) {

        return findAvailableCars(startDate, endDate, spec);
    }

    private List<CarResponseDto> findAvailableCars(Date startDate, Date endDate, Specification<Car> spec){

        List<Car> carList = carRepository.findAll(spec);
        List<Reservation> reservationList = reservationRepository.findByEndDateIsAfterAndStartDateIsBeforeOrderByStartDate
                (startDate, endDate)
                .orElse(null);

        if (reservationList == null) return carList.stream().map(carMapper::carToCarPresentDto).collect(Collectors.toList());

        for(Reservation reservation: reservationList){
                carList.removeIf(car -> car.getId().equals(reservation.getCar().getId()));
        }

        return carList.stream().map(carMapper::carToCarPresentDto).collect(Collectors.toList());
    }

    private CustomUserDetails getUserDetails(){

        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
