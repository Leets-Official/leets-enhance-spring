package leets.enhance.domain.item.dao;

import leets.enhance.domain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findByUserId(Long id);
    List<Item> findTop10ByOrderByLevelDesc();
}
