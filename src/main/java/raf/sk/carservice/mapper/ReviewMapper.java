package raf.sk.carservice.mapper;

import org.springframework.stereotype.Component;
import raf.sk.carservice.dto.reviewDto.ReviewCreateDto;
import raf.sk.carservice.dto.reviewDto.ReviewPresentDto;
import raf.sk.carservice.model.Review;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {
    public ReviewPresentDto toDto(Review review){
        ReviewPresentDto dto = new ReviewPresentDto();
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setCreatorUsername(review.getCreatorUsername());

        return dto;
    }

    public Review toReview(ReviewCreateDto dto){
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setCreatorUsername(dto.getCreatorUsername());

        return review;
    }

    public List<ReviewPresentDto> toDtoList(List<Review> reviewList){
        List<ReviewPresentDto> resultList = new ArrayList<>();
        for(Review review: reviewList){
            resultList.add(toDto(review));
        }

        return resultList;
    }
}
