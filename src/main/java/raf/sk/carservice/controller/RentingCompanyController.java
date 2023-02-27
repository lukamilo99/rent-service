package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.company.RentingCompanyRequestDto;
import raf.sk.carservice.dto.company.RentingCompanyResponseDto;
import raf.sk.carservice.service.implementation.RentingCompanyServiceImplementation;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class RentingCompanyController {

    private RentingCompanyServiceImplementation rentingCompanyService;

    @PutMapping("update")
    public ResponseEntity<RentingCompanyRequestDto> updateRentingCompany(@RequestBody RentingCompanyRequestDto rentingCompanyRequestDto){
        rentingCompanyService.updateRentingCompany(rentingCompanyRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RentingCompanyResponseDto> findManagerById(@PathVariable Long id){
        rentingCompanyService.findByManagerId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
