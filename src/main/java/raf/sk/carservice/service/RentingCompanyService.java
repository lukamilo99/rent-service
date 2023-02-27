package raf.sk.carservice.service;

import raf.sk.carservice.dto.company.RentingCompanyRequestDto;
import raf.sk.carservice.dto.company.RentingCompanyResponseDto;

import java.util.List;

public interface RentingCompanyService {

    void updateRentingCompany(RentingCompanyRequestDto rentingCompanyRequestDto);
    List<RentingCompanyResponseDto> findByManagerId(Long id);
}
