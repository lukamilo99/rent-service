package raf.sk.carservice.dto.car;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarRequestDto {

    private String brand;
    private String model;
    private String type;
    private BigDecimal pricePerDay;
}
