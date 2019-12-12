package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.controller;

import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GrnQuantity;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.ItemQuantity;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/grn")
public class GoodReceivingManagementController {
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final PurchaseOrderService purchaseOrderService;
    private final LedgerService ledgerService;
    private final DateTimeAgeService dateTimeAgeService;
    private final ItemService itemService;

    @Autowired
    public GoodReceivingManagementController(GoodReceivingManagementService goodReceivingManagementService, PurchaseOrderService purchaseOrderService, LedgerService ledgerService, DateTimeAgeService dateTimeAgeService, ItemService itemService) {
        this.goodReceivingManagementService = goodReceivingManagementService;
        this.purchaseOrderService = purchaseOrderService;
        this.ledgerService = ledgerService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.itemService = itemService;
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
        goodReceivingManagement.setPurchaseOrder(purchaseOrder1);
        goodReceivingManagement.setSupplier(purchaseOrder1.getSupplier());

        model.addAttribute("grn", goodReceivingManagement);
//        model.addAttribute("supplier", );
//        model.addAttribute("purchaseOrder1", purchaseOrder1);
        model.addAttribute("searchArea", false);
        model.addAttribute("addStatus", true);
        return "grn/addGrn";
    }

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addGRN(@Valid @ModelAttribute("grn")GoodReceivingManagement goodReceivingManagement, BindingResult result, Model model, RedirectAttributes redirectAttributes){
//        GoodReceivingManagement goodReceivingManagement1 = goodReceivingManagement;
        System.out.println(goodReceivingManagement.toString());
        List<GrnQuantity> grnQuantities = new ArrayList<>();
        for(GrnQuantity grnQuantity : goodReceivingManagement.getGrnQuantities()){
            Ledger ledger = ledgerService.findByItem(grnQuantity.getItem());
            if (ledger !=null){
                if (ledger.getItem().getCost().equals(grnQuantity.getItem().getCost())){
                    ledger.setAvailableQuantity(ledger.getAvailableQuantity() + grnQuantity.getReceivedQuantity());
                    Item item= itemService.findById(grnQuantity.getItem().getId());
                    item.setSoh(ledger.getAvailableQuantity());
                    itemService.persist(item);
                    ledger.setUpdatedAt(dateTimeAgeService.getCurrentDate());
               grnQuantity.setItem(ledgerService.persist(ledger).getItem());
               grnQuantities.add(grnQuantity);
                } else {
                    Item item =ledger.getItem();
//                    item.setId(null);
                    String c = "";
                    if (itemService.lastItem() == null) {
                         c = "EHS00";
                    } else {
                        c = itemService.lastItem().getCode();
                    }
                    String itemNumber = c.replaceAll("[^0-9]+", "");
                    Integer ItemNumber = Integer.parseInt(itemNumber);
                    int newItemNumber = ItemNumber + 1;
                    item.setCode("EHS" + newItemNumber);
//                    item.setDescription(grnQuantity.getItem().getDescription());
                    item.setCost(grnQuantity.getItem().getCost());
                    item.setSelling(grnQuantity.getItem().getSelling());
//                    item.setCategory(grnQuantity.getItem().getCategory());
//                    item.setGeneric(grnQuantity.getItem().getGeneric());
//                    item.setCreatedAt(dateTimeAgeService.getCurrentDate());
                    item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
                 ledger.setId(ledgerService.getLastItemId().getId()+1);
                 ledger.setCost(grnQuantity.getItem().getCost());
                 ledger.setSalePrice(grnQuantity.getItem().getSelling());
                 ledger.setCreatedAt(dateTimeAgeService.getCurrentDate());
                 ledger.setUpdatedAt(dateTimeAgeService.getCurrentDate());
                ledger.setAvailableQuantity(grnQuantity.getReceivedQuantity());
                    grnQuantity.setItem(ledgerService.persist(ledger).getItem());
                    grnQuantities.add(grnQuantity);
                }
            }
        }

        goodReceivingManagement.setCreatedDate(dateTimeAgeService.getCurrentDate());
        goodReceivingManagement.setUpdatedDate(dateTimeAgeService.getCurrentDate());
        goodReceivingManagement.setGrnQuantities(grnQuantities);
        goodReceivingManagementService.persist(goodReceivingManagement);
        return "redirect:/grn";
    }
}
