package raf.sk.carservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentingCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String numberOfVehicle;
    private String city;
    private Long managerId;
    @OneToMany(mappedBy = "ownerCompany")
    private List<Car> carForRentList;
    @OneToMany(mappedBy = "rentingCompany")
    private List<Review> reviewList;
}
