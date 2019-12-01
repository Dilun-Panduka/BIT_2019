package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.controller;

import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.Enum.GRNStatus;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/grn")
public class GoodReceivingManagementController {

    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public GoodReceivingManagementController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/add")
    public String grnAddForm(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("grnStatus", GRNStatus.values());
        model.addAttribute("poCode", purchaseOrderService.findAll());
        model.addAttribute("grn", new GoodReceivingManagement());
        return "grn/addGrn";
    }
}
