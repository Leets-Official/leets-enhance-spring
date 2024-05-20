package leets.enhance.domain.Blade.domain.repository;

import leets.enhance.domain.Blade.domain.Blade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BladeRepository extends JpaRepository<Blade, UUID> {

}
