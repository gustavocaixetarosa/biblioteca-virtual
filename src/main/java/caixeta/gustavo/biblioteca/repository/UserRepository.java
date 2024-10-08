package caixeta.gustavo.biblioteca.repository;

import caixeta.gustavo.biblioteca.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
