package leets.enhance.repository;


import leets.enhance.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findTop10ByOrderByLevelDesc();
}