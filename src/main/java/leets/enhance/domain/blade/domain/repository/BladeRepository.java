package leets.enhance.domain.blade.domain.repository;

import leets.enhance.domain.blade.domain.Blade;
import leets.enhance.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BladeRepository extends JpaRepository<Blade, UUID> {
    Blade findByUser(User user);

    List<Blade> findTop10ByOrderByLevelDesc();
}
