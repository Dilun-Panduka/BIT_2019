package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.controller;

import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GrnQuantity;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.ItemQuantity;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/grn")
public class GoodReceivingManagementController {
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public GoodReceivingManagementController(GoodReceivingManagementService goodReceivingManagementService, PurchaseOrderService purchaseOrderService) {
        this.goodReceivingManagementService = goodReceivingManagementService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/add")
    public String grnAddForm(Model model) {
        List<String> codesList = new ArrayList<>();
        purchaseOrderService.findAll().forEach(purchaseOrder -> codesList.add(purchaseOrder.getCode()));
        model.addAttribute("searchArea", true);
        model.addAttribute("addStatus", true);
        model.addAttribute("pOList", codesList);
        return "grn/addGrn";
    }

    @PostMapping("/findByPOCode")
    public String searchByPOCode(@Valid @ModelAttribute("code") String code, Model model) {
        PurchaseOrder purchaseOrder1 = purchaseOrderService.findByCode(code);
        List<ItemQuantity> itemQuantities = purchaseOrder1.getItemQuantity();

        List<GrnQuantity> grnQuantities = new ArrayList<>();
        for (ItemQuantity itemQuantity : itemQuantities) {
            GrnQuantity grnQuantity = new GrnQuantity();
            grnQuantity.setItem(itemQuantity.getItem());
            grnQuantity.setRequestedQty(itemQuantity.getQuantity());
            grnQuantity.setReceivedQuantity(0);
            grnQuantities.add(grnQuantity);
        }
        GoodReceivingManagement goodReceivingManagement = new GoodReceivingManagement();
        goodReceivingManagement.setGrnQuantities(grnQuantities);

        model.addAttribute("grn", goodReceivingManagement);
        model.addAttribute("supplier", purchaseOrder1.getSupplier());
        model.addAttribute("purchaseOrder", purchaseOrder1);
        model.addAttribute("searchArea", false);
        model.addAttribute("addStatus", true);
        return "grn/addGrn";
    }
}
