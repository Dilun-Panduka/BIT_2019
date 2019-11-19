package lk.excellent.pharamacy_management.asset.process.purchaseOrder.controller;

import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.excellent.pharamacy_management.asset.suppliers.service.SupplierService;
import lk.excellent.pharamacy_management.security.service.UserService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final SupplierService supplierService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, DateTimeAgeService dateTimeAgeService, UserService userService, SupplierService supplierService) {
        this.purchaseOrderService = purchaseOrderService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.supplierService = supplierService;
    }

    @RequestMapping
    public String puchaseOrderPage(Model model){
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll();
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "purchaseOrder/purchaseOrder";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String purchaseOrderView(@PathVariable("id") Integer id, Model model){
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);
        model.addAttribute("purchaseOrderDetail", purchaseOrder);
        model.addAttribute("addStatus", false);
        return "purchaseOrder/purchaseOrder-detail";
    }

    @GetMapping("/add")
    public String purchaseOrderAdd(Model model){
        model.addAttribute("addStatus", true);
        model.addAttribute("supplier", supplierService.findAll());
        model.addAttribute("status", PurchaseOrderStatus.values());
        model.addAttribute("purchesOrder", new PurchaseOrder());
        return "purchaseOrder/addPurchaseOrder";
    }

}
