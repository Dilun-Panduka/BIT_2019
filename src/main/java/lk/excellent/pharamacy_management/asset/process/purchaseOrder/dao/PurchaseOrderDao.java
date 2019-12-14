package lk.excellent.pharamacy_management.asset.process.purchaseOrder.dao;

import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {

    PurchaseOrder findFirstByOrderByIdDesc();

    PurchaseOrder findByCode(String code);

    List<PurchaseOrder> findByCreatedDateBetween(LocalDate from, LocalDate to);

    List<PurchaseOrder> findBySupplier(Supplier supplier);

    List<PurchaseOrder> findByExpectedDate(LocalDate date);
}
