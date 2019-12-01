package lk.excellent.pharamacy_management.asset.item.dao;

import lk.excellent.pharamacy_management.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    Item findFirstByOrderByIdDesc();

    Item findByCode(String code);

}
