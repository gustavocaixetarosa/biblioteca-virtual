package caixeta.gustavo.biblioteca.repository;

import caixeta.gustavo.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByDocument(String document);
//
//    long countReservationsByUser_Id(Long userId);
}
