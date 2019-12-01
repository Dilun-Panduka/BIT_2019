package lk.excellent.pharamacy_management.asset.process.generalLedger.dao;


import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerDao extends JpaRepository<Ledger, Integer > {

    Ledger findFirstByOrderByIdDesc();

    Ledger findByItem(Item item);
}
