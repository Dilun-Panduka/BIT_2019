package lk.excellent.pharamacy_management.asset.process.purchaseOrder.dao;

import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {

    PurchaseOrder findFirstByOrderByIdDesc();

    PurchaseOrder findByCode(String code);
}
