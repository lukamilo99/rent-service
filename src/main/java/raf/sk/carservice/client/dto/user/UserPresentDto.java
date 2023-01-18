package raf.sk.carservice.client.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.sk.carservice.client.dto.rank.RankPresentDto;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
public class UserPresentDto {
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Integer numberOfRentDays;
    private RankPresentDto rank;
}
