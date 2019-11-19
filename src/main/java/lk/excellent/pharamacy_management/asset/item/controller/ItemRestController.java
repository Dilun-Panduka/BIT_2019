package lk.excellent.pharamacy_management.asset.item.controller;

import lk.excellent.pharamacy_management.asset.commonAsset.entity.SupplierItem;
import lk.excellent.pharamacy_management.asset.commonAsset.service.SupplierItemService;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restItem")
public class ItemRestController {
    private final SupplierItemService supplierItemService;

    @Autowired
    public ItemRestController(SupplierItemService supplierItemService) {
        this.supplierItemService = supplierItemService;
    }
    /*List<Item>*/
    @GetMapping("/{supplier}")
    public void   getItem(@PathVariable("supplier")Supplier supplier) {
       List<SupplierItem> supplierItems = supplierItemService.findBySupplier(supplier);


    }
}
