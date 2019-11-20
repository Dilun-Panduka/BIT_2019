package lk.excellent.pharamacy_management.asset.commonAsset.service;

import lk.excellent.pharamacy_management.asset.commonAsset.dao.SupplierItemDao;
import lk.excellent.pharamacy_management.asset.commonAsset.entity.SupplierItem;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierItemService {
    private final SupplierItemDao supplierItemDao;

    @Autowired
    public SupplierItemService(SupplierItemDao supplierItemDao) {
        this.supplierItemDao = supplierItemDao;
    }

    public void persist(SupplierItem supplierItem){
        supplierItemDao.save(supplierItem);
    }

    public List<SupplierItem> findBySupplier(Supplier supplier){
        List<SupplierItem> supplierItems = supplierItemDao.findBySupplier(supplier);
        supplierItemDao.deleteAll(supplierItems);
        return supplierItems;
    }

    public List<SupplierItem> findItems(Supplier supplier){
        return supplierItemDao.findBySupplier(supplier);
    }

    public SupplierItem lastSupplierItem(){
        return supplierItemDao.findFirstByOrderByIdDesc();
    }

    public List<SupplierItem> findSupplier(Item item) {
        return supplierItemDao.findByItem(item);
    }



}
