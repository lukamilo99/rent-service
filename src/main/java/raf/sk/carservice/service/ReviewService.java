package raf.sk.carservice.service;

import raf.sk.carservice.dto.reviewDto.ReviewCreateDto;
import raf.sk.carservice.dto.reviewDto.ReviewPresentDto;

import java.util.List;

public interface ReviewService {
    List<ReviewPresentDto> findReviewByRentingCompanyName(String name);
    List<ReviewPresentDto> findReviewByRentingCompanyCity(String name);

    void deleteById(Long id);
    void save(ReviewCreateDto dto);
}
