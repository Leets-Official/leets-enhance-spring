package leets.enhance.domain.weapon.repository;

import leets.enhance.domain.user.domain.User;
import leets.enhance.domain.weapon.domain.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeaponRepository extends JpaRepository<Weapon,Long> {
    Optional<Weapon> findByUser(User user);
}
