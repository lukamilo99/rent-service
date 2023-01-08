package raf.sk.carservice.service;

import raf.sk.carservice.dto.RentingCompanyCreateDto;
import raf.sk.carservice.model.RentingCompany;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RentingCompanyService {
    public void updateRentingCompany(Long id, RentingCompanyCreateDto rentingCompanyCreateDto);

    public Optional<RentingCompany> findByManagerId(Long id);
}
