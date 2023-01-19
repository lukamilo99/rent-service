package raf.sk.carservice.dto.reservationDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
public class ReservationCreateDto {
    private Long carId;
    private Long userId;
    private Date startDate;
    private Date endDate;
}
