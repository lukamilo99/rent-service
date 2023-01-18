package raf.sk.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.sk.carservice.model.Reservation;


import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findByEndDateIsAfterAndCar_IdOrderByStartDate(Date endDate, Long car_id);
    Optional<List<Reservation>> findByEndDateIsAfterAndStartDateIsBeforeOrderByStartDate(Date endDate, Date startDate);
    Optional<Reservation> findById(Long id);
    void deleteById(Long id);
}
