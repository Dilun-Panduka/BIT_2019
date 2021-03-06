package lk.excellent.pharamacy_management.asset.process.generalLedger.service;


import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.process.generalLedger.dao.LedgerDao;
import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerService implements AbstractService<Ledger, Integer > { private final LedgerDao ledgerDao;

    @Autowired
    public LedgerService(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }





    @Override
    public List<Ledger> findAll() {
        return ledgerDao.findAll();
    }

    @Override
    public Ledger findById(Integer id) {
        return null;
    }

    @Override
    public Ledger persist(Ledger ledger) {
        return ledgerDao.save(ledger);
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public List<Ledger> search(Ledger ledger) {
        return null;
    }

    public Ledger getLastItemId() {
        return ledgerDao.findFirstByOrderByIdDesc();
    }

    public Ledger findByItem(Item item) {
        return ledgerDao.findByItem(item);
    }
}
