package leets.enhance.domain.user.domain.repository;

import leets.enhance.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String email);

    Boolean existsByUsername(String email);
}