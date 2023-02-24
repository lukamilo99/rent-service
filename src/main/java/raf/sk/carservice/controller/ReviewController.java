package raf.sk.carservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.carservice.dto.review.ReviewRequestDto;
import raf.sk.carservice.dto.review.ReviewResponseDto;
import raf.sk.carservice.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;
    @PostMapping("/create")
    public ResponseEntity<Void> createReview(@RequestBody ReviewRequestDto dto){
        reviewService.save(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/name")
    public ResponseEntity<List<ReviewResponseDto>> findByCompanyName(@RequestParam String companyName){
        return new ResponseEntity<>(reviewService.findReviewByRentingCompanyName(companyName), HttpStatus.OK);
    }

    @GetMapping("/find/city")
    public ResponseEntity<List<ReviewResponseDto>> findByCity(@RequestParam String city){
        return new ResponseEntity<>(reviewService.findReviewByRentingCompanyCity(city), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        reviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
