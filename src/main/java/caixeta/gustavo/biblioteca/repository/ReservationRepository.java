package caixeta.gustavo.biblioteca.repository;

import caixeta.gustavo.biblioteca.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    long countByUser_Id(Long userId);

    long countByUser_IdAndIsReturnedFalse(Long userId);

    List<Reservation> findByUserIdAndIsReturnedFalse(Long userId);
}
