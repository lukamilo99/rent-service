package raf.sk.carservice.service;

import raf.sk.carservice.dto.company.RentingCompanyRequestDto;
import raf.sk.carservice.dto.company.RentingCompanyResponseDto;

import java.util.List;

public interface RentingCompanyService {
    public void updateRentingCompany(RentingCompanyRequestDto rentingCompanyRequestDto);
    public List<RentingCompanyResponseDto> findByManagerId(Long id);
}
