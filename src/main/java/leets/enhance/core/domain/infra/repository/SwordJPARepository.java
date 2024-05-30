package leets.enhance.core.domain.infra.repository;

import leets.enhance.core.domain.entity.Sword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwordJPARepository extends JpaRepository<Sword, Long> {

}
