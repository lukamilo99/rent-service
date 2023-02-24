package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.company.RentingCompanyRequestDto;
import raf.sk.carservice.dto.company.RentingCompanyResponseDto;
import raf.sk.carservice.exception.CompanyNotFoundException;
import raf.sk.carservice.mapper.RentingCompanyMapper;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.security.model.CustomUserDetails;
import raf.sk.carservice.service.RentingCompanyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentingCompanyServiceImplementation implements RentingCompanyService {
    private RentingCompanyRepository rentingCompanyRepository;
    private RentingCompanyMapper companyMapper;
    @Override
    public void updateRentingCompany(RentingCompanyRequestDto rentingCompanyRequestDto) {
        CustomUserDetails userDetails = getUserDetails();
        RentingCompany rentingCompany = rentingCompanyRepository.findById(userDetails.getId()).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        if(rentingCompanyRequestDto.getName() != null) rentingCompany.setName(rentingCompanyRequestDto.getName());
        if(rentingCompanyRequestDto.getDescription() != null) rentingCompany.setDescription(rentingCompanyRequestDto.getDescription());
    }

    @Override
    public List<RentingCompanyResponseDto> findByManagerId(Long id) {
        List<RentingCompany> rentingCompanyList = rentingCompanyRepository.findByManagerId(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        return rentingCompanyList.stream().map(company -> companyMapper.rentingCompanyToRentingCompanyResponseDto(company)).collect(Collectors.toList());
    }

    private CustomUserDetails getUserDetails(){
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
