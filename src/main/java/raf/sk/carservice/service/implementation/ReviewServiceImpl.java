package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.review.ReviewRequestDto;
import raf.sk.carservice.dto.review.ReviewResponseDto;
import raf.sk.carservice.exception.CompanyNotFoundException;
import raf.sk.carservice.exception.ReviewNotFoundException;
import raf.sk.carservice.mapper.ReviewMapper;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.model.Review;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.repository.ReviewRepository;
import raf.sk.carservice.service.ReviewService;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private RentingCompanyRepository rentingCompanyRepository;
    private ReviewMapper reviewMapper;
    @Override
    public List<ReviewResponseDto> findReviewByRentingCompanyName(String name) {
        List<Review> reviewList = reviewRepository.findReviewByRentingCompany_Name(name).orElseThrow(() -> new ReviewNotFoundException("No reviews found for company"));

        return reviewMapper.toDtoList(reviewList);
     }

    @Override
    public List<ReviewResponseDto> findReviewByRentingCompanyCity(String city) {
        List<Review> reviewList = reviewRepository.findReviewByRentingCompany_City(city).orElseThrow(() -> new ReviewNotFoundException("No reviews found for city"));

        return reviewMapper.toDtoList(reviewList);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void save(ReviewRequestDto dto) {
        Review review = reviewMapper.toReview(dto);
        RentingCompany rentingCompany = rentingCompanyRepository.findById(dto.getRentingCompanyId()).orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        review.setRentingCompany(rentingCompany);
        reviewRepository.save(review);
    }
}
