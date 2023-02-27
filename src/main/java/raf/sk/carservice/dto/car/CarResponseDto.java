package raf.sk.carservice.dto.car;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarResponseDto {

    private Long id;
    private String brand;
    private String model;
    private String type;
    private BigDecimal pricePerDay;
    private String rentingCompanyName;
    private String city;
}
