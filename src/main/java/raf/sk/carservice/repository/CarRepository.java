package raf.sk.carservice.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.carservice.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<List<Car>> findCarByBrand(String brand);
    Optional<List<Car>> findCarByType(String type);
    Optional<List<Car>> findCarByModel(String model);
    List<Car> findAll(Specification<Car> specification) ;
}
