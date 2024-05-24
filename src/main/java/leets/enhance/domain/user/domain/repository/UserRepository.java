package leets.enhance.domain.user.domain.repository;

import leets.enhance.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String email);
    User findByUid(UUID uid);

    boolean existsByUsername(String email);
}