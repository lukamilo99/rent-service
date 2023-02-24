package raf.sk.carservice.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDto {
    private Long carId;
    private Date startDate;
    private Date endDate;
}
