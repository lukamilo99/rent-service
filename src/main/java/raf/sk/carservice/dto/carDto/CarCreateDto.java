package raf.sk.carservice.dto.carDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CarCreateDto {
    private String brand;
    private String model;
    private String type;
    private BigDecimal pricePerDay;
    private Long rentingCompanyId;
}
