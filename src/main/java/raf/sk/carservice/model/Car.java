package raf.sk.carservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String type;
    private BigDecimal pricePerDay;
    @ManyToOne
    private RentingCompany ownerCompany;
    @OneToMany(mappedBy = "car")
    private List<Reservation> listOfReservations;
}
