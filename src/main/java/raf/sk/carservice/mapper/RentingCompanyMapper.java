package raf.sk.carservice.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.RentingCompanyCreateDto;
import raf.sk.carservice.model.RentingCompany;

@Component
@NoArgsConstructor
public class RentingCompanyMapper {

    public RentingCompany RentingCompanyCreateDtoToRentingCompany(RentingCompanyCreateDto rentingCompanyCreateDto){
        RentingCompany rentingCompany = new RentingCompany();
        rentingCompany.setName(rentingCompanyCreateDto.getName());
        rentingCompany.setDescription(rentingCompanyCreateDto.getDescription());
        rentingCompany.setCity(rentingCompanyCreateDto.getCity());

        return rentingCompany;
    }
}
