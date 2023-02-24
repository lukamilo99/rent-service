package raf.sk.carservice.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RentingCompanyRequestDto {
    private String name;
    private String description;
    private String city;
}
