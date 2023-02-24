package raf.sk.carservice.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.company.RentingCompanyRequestDto;
import raf.sk.carservice.dto.company.RentingCompanyResponseDto;
import raf.sk.carservice.model.RentingCompany;

@Component
@NoArgsConstructor
public class RentingCompanyMapper {

    public RentingCompany rentingCompanyRequestDtoToRentingCompany(RentingCompanyRequestDto rentingCompanyRequestDto){
        RentingCompany rentingCompany = new RentingCompany();

        rentingCompany.setName(rentingCompanyRequestDto.getName());
        rentingCompany.setDescription(rentingCompanyRequestDto.getDescription());
        rentingCompany.setCity(rentingCompanyRequestDto.getCity());

        return rentingCompany;
    }

    public RentingCompanyResponseDto rentingCompanyToRentingCompanyResponseDto(RentingCompany rentingCompany){
        RentingCompanyResponseDto dto = new RentingCompanyResponseDto();

        dto.setName(rentingCompany.getName());
        dto.setDescription(rentingCompany.getDescription());
        dto.setCity(rentingCompany.getCity());

        return dto;
    }
}
