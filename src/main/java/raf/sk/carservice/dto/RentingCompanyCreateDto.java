package raf.sk.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RentingCompanyCreateDto {
    private String name;
    private String description;
    private String city;
}
