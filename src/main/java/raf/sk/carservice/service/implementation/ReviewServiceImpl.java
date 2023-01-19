package raf.sk.carservice.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.sk.carservice.dto.reviewDto.ReviewCreateDto;
import raf.sk.carservice.dto.reviewDto.ReviewPresentDto;
import raf.sk.carservice.mapper.ReviewMapper;
import raf.sk.carservice.model.RentingCompany;
import raf.sk.carservice.model.Review;
import raf.sk.carservice.repository.RentingCompanyRepository;
import raf.sk.carservice.repository.ReviewRepository;
import raf.sk.carservice.service.ReviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private RentingCompanyRepository rentingCompanyRepository;
    private ReviewMapper reviewMapper;
    @Override
    public List<ReviewPresentDto> findReviewByRentingCompanyName(String name) {
        Optional<List<Review>> reviewList = reviewRepository.findReviewByRentingCompany_Name(name);
        List<ReviewPresentDto> resultList = new ArrayList<>();

        if(reviewList.isPresent()){
            resultList = reviewMapper.toDtoList(reviewList.get());
        }
        return resultList;
     }

    @Override
    public List<ReviewPresentDto> findReviewByRentingCompanyCity(String city) {
        Optional<List<Review>> reviewList = reviewRepository.findReviewByRentingCompany_City(city);
        List<ReviewPresentDto> resultList = new ArrayList<>();

        if(reviewList.isPresent()){
            resultList = reviewMapper.toDtoList(reviewList.get());
        }
        return resultList;
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void save(ReviewCreateDto dto) {
        Review review = reviewMapper.toReview(dto);
        Optional<RentingCompany> rentingCompany = rentingCompanyRepository.findById(dto.getRentingCompanyId());

        if(rentingCompany.isPresent()){
            review.setRentingCompany(rentingCompany.get());
            reviewRepository.save(review);
        }
        else System.out.println("There is no company with id" + dto.getRentingCompanyId());
    }
}
