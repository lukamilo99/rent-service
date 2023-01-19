package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.reviewDto.ReviewCreateDto;
import raf.sk.carservice.dto.reviewDto.ReviewPresentDto;
import raf.sk.carservice.security.CheckPrivilege;
import raf.sk.carservice.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;
    @PostMapping("/create")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<Void> createReview(@RequestHeader("Authorization") String authorization, @RequestBody ReviewCreateDto dto){
        reviewService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/name")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT", "MANAGER"})
    public ResponseEntity<List<ReviewPresentDto>> findByCompanyName(@RequestHeader("Authorization") String authorization, @RequestParam String companyName){
        return new ResponseEntity<>(reviewService.findReviewByRentingCompanyName(companyName), HttpStatus.OK);
    }

    @GetMapping("/find/city")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT", "MANAGER"})
    public ResponseEntity<List<ReviewPresentDto>> findByCity(@RequestHeader("Authorization") String authorization, @RequestParam String city){
        return new ResponseEntity<>(reviewService.findReviewByRentingCompanyCity(city), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @CheckPrivilege(roles = {"ADMIN", "CLIENT"})
    public ResponseEntity<Void> deleteById(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        reviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
