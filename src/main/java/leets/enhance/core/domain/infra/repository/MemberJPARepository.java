package leets.enhance.core.domain.infra.repository;

import java.util.Optional;
import leets.enhance.core.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPARepository extends JpaRepository<Member, Long> {

  Boolean existsByEmail(String email);

  Optional<Member> findByEmail(String email);
}
