package raf.sk.carservice.service;

import raf.sk.carservice.dto.review.ReviewRequestDto;
import raf.sk.carservice.dto.review.ReviewResponseDto;

import java.util.List;

public interface ReviewService {
    List<ReviewResponseDto> findReviewByRentingCompanyName(String name);
    List<ReviewResponseDto> findReviewByRentingCompanyCity(String name);

    void deleteById(Long id);
    void save(ReviewRequestDto dto);
}
