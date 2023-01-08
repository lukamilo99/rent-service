package raf.sk.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import raf.sk.carservice.repository.CarRepository;

@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CarServiceApplication.class, args);
    }

}
