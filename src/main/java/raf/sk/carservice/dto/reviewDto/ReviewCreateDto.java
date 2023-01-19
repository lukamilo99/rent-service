package raf.sk.carservice.dto.reviewDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewCreateDto {
    private Long rentingCompanyId;
    private String rating;
    private String comment;
    private String creatorUsername;
}
