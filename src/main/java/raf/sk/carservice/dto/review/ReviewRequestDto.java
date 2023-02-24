package raf.sk.carservice.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long rentingCompanyId;
    private String rating;
    private String comment;
    private String creatorUsername;
}
