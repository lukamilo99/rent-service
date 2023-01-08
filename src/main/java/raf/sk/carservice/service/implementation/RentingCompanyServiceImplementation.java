package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.RentingCompanyCreateDto;
import raf.sk.carservice.mapper.RentingCompanyMapper;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.service.RentingCompanyService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RentingCompanyServiceImplementation implements RentingCompanyService {
    private RentingCompanyRepository rentingCompanyRepository;
    private RentingCompanyMapper rentingCompanyMapper;
    @Override
    public void updateRentingCompany(Long id, RentingCompanyCreateDto rentingCompanyCreateDto) {
        Optional<RentingCompany> rentingCompany = rentingCompanyRepository.findById(id);
        if(rentingCompany.isEmpty()) return;
        if(rentingCompanyCreateDto.getName() != null) rentingCompany.get().setName(rentingCompanyCreateDto.getName());
        if(rentingCompanyCreateDto.getDescription() != null) rentingCompany.get().setDescription(rentingCompanyCreateDto.getDescription());
        rentingCompanyRepository.save(rentingCompany.get());
    }

    @Override
    public Optional<RentingCompany> findByManagerId(Long id) {
        return rentingCompanyRepository.findByManagerId(id);
    }
}
