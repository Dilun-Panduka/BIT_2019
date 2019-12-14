package lk.excellent.pharamacy_management.asset.item.dao;

import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Category;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    List<Item> findByCategory(Category category);

    Item findFirstByOrderByIdDesc();

    Item findByCode(String code);

    List<Item> countByCategory(Category category);

    List<Item> findByCreatedAtBetween(LocalDate from, LocalDate to);

}
