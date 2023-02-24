package raf.sk.carservice.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {
    private String rentingCompanyName;
    private String rating;
    private String comment;
    private String creatorUsername;
}
