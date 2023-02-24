package raf.sk.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.carservice.model.RentingCompany;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentingCompanyRepository extends JpaRepository<RentingCompany, Long> {
    Optional<List<RentingCompany>> findByManagerId(Long id);
}
