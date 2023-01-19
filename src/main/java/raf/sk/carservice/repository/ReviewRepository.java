package raf.sk.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.carservice.model.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findReviewByRentingCompany_Name(String name);
    Optional<List<Review>> findReviewByRentingCompany_City(String name);
}
