package raf.sk.carservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.review.ReviewRequestDto;
import raf.sk.carservice.dto.review.ReviewResponseDto;
import raf.sk.carservice.model.Review;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {
    public ReviewResponseDto toDto(Review review){
        ReviewResponseDto dto = new ReviewResponseDto();

        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setCreatorUsername(review.getCreatorUsername());

        return dto;
    }

    public Review toReview(ReviewRequestDto dto){
        Review review = new Review();

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatorUsername(dto.getCreatorUsername());

        return review;
    }

    public List<ReviewResponseDto> toDtoList(List<Review> reviewList){
        List<ReviewResponseDto> resultList = new ArrayList<>();

        for(Review review: reviewList){
            resultList.add(toDto(review));
        }

        return resultList;
    }
}
