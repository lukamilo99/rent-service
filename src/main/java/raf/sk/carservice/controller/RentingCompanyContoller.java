package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.RentingCompanyCreateDto;
import raf.sk.carservice.security.CheckPrivilege;
import raf.sk.carservice.service.implementation.RentingCompanyServiceImplementation;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class RentingCompanyContoller {
    private RentingCompanyServiceImplementation rentingCompanyService;
    @PutMapping("update/{id}")
    @CheckPrivilege(roles = {"ADMIN", "MANAGER"})
    public ResponseEntity<RentingCompanyCreateDto> updateRentingCompany(@RequestHeader("Authorization") String authorization, @PathVariable Long id, @RequestBody RentingCompanyCreateDto rentingCompanyCreateDto){
        rentingCompanyService.updateRentingCompany(id, rentingCompanyCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @CheckPrivilege(roles = {"ADMIN", "MANAGER", "CLIENT"})
    @GetMapping("/findManager/{id}")
    public ResponseEntity<RentingCompanyCreateDto> findManagerById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        rentingCompanyService.findByManagerId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
