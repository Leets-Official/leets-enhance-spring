package leets.enhance.domain.item.repository;

import leets.enhance.domain.item.domain.Item;
import leets.enhance.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByUser(User user);
}
