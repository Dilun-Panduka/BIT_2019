package lk.excellent.pharamacy_management.asset.process.purchaseOrder.controller;

import lk.excellent.pharamacy_management.asset.commonAsset.service.SupplierItemService;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.ItemQuantity;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lk.excellent.pharamacy_management.asset.suppliers.service.SupplierService;
import lk.excellent.pharamacy_management.security.service.UserService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final SupplierItemService supplierItemService;
    private final ItemService itemService;
    private final LedgerService ledgerService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, DateTimeAgeService dateTimeAgeService, UserService userService, SupplierService supplierService, SupplierItemService supplierItemService, ItemService itemService, LedgerService ledgerService) {
        this.purchaseOrderService = purchaseOrderService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.supplierItemService = supplierItemService;
        this.itemService = itemService;
        this.ledgerService = ledgerService;
    }

    //give all PO to frontend
    @RequestMapping
    public String purchaseOrderPage(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll().stream().
                filter(x -> x.getPurchaseOrderStatus().equals(PurchaseOrderStatus.NOT) || x.getPurchaseOrderStatus().equals(PurchaseOrderStatus.PARTIALY))
                .collect(Collectors.toList());
        model.addAttribute("purchaseOrders", purchaseOrders);
        return "purchaseOrder/purchaseOrder";
    }

    //give PO details
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String purchaseOrderView(@PathVariable("id") Integer id, Model model) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);
        model.addAttribute("purchaseOrderDetail", purchaseOrder);
        model.addAttribute("addStatus", false);
        return "purchaseOrder/purchaseOrder-detail";
    }

    // purchase order edit
    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String purchaseOrderEdit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("searchArea", false);
        model.addAttribute("addStatus", false);
        model.addAttribute("purchaseOrder", purchaseOrderService.findById(id));
        model.addAttribute("purchaseOrderStatus", PurchaseOrderStatus.values());
        return "purchaseOrder/addPurchaseOrder";
    }

    //OP add  given start
    @GetMapping("/addFind")
    public String pOAddForm(Model model) {
        model.addAttribute("searchArea", true);
        model.addAttribute("addStatus", true);
        return "purchaseOrder/addPurchaseOrder";
    }

    private Supplier commonSearch(Supplier supplier) {
        Supplier supplier1 = supplier;
        if (supplier.getCode() != null) {
            supplier1 = supplierService.findByCode(supplier.getCode());
        }
        if (supplier.getName() != null) {
            supplier1 = supplierService.findByName(supplier.getName());
        }
        return supplier1;
    }

    private String commonPurchase(Model model) {
        model.addAttribute("searchArea", false);
        model.addAttribute("addStatus", true);
        model.addAttribute("multipleSupplier", false);
        return "purchaseOrder/addPurchaseOrder";
    }

    //Get supplier code or name to find suppliers
    @PostMapping("/findBySupplier")
    public String searchBySupplier(@Valid @ModelAttribute("supplier") Supplier supplier, Model model) {

        List<Supplier> suppliers = supplierService.search(supplier);
        List<Item> items = new ArrayList<>();
        if (suppliers.size() == 1) {
            supplierItemService.findBySupplier1(suppliers.get(0))
                    .forEach((supplierItem) -> items.add(supplierItem.getItem()));

            List<ItemQuantity> itemQuantities = new ArrayList<>();

            for (Item item : items) {
                ItemQuantity itemQuantity = new ItemQuantity();
                itemQuantity.setItem(item);
                itemQuantity.setQuantity(0);
                itemQuantities.add(itemQuantity);
            }
            //create a new purchase order
            PurchaseOrder purchaseOrder = new PurchaseOrder();

            purchaseOrder.setItemQuantity(itemQuantities);

            model.addAttribute("purchaseOrder", purchaseOrder);
            model.addAttribute("supplier", suppliers.get(0));
            return commonPurchase(model);
        }
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("multipleSupplier", true);

        return "purchaseOrder/addPurchaseOrder";
    }

    @RequestMapping(value = {"/add", "/update"}, method = RequestMethod.POST)
    public String addPurchaseOrder(@Valid @ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrder, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
        }
        System.out.println("purchase reviwdw " + purchaseOrder.getSupplier());
        try {
            String input = "";
            if (purchaseOrderService.findLastPONumber() == null) {
                input = "EHSPO1";
            } else {
                input = purchaseOrderService.findLastPONumber().getCode();
            }
            String po = input.replaceAll("[^0-9]+", "");
            Integer PONumber = Integer.parseInt(po);
            int newPONumber = PONumber + 1;
            purchaseOrder.setCode("EHSPO" + newPONumber);
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.NOT);
            purchaseOrder.setCreatedDate(dateTimeAgeService.getCurrentDate());
            purchaseOrder.setUpdatedDate(dateTimeAgeService.getCurrentDate());
//            List<ItemQuantity> itemQuantities = new ArrayList<>();
            for (ItemQuantity itemQuantity : purchaseOrder.getItemQuantity()) {
                if(!(itemQuantity.getQuantity() == 0)) {
                    itemQuantity.setPurchaseOrder(purchaseOrder);
                    itemQuantity.setAmount(itemQuantity.getAmount());
                }

            }
//        save purchase order
            PurchaseOrder purchaseOrder1 = purchaseOrderService.persist(purchaseOrder);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        System.out.println(purchaseOrder.toString());
        return "redirect:/purchaseOrder";
    }

    /* List<Item> items = new ArrayList<>();
        Supplier supplier1 = commonSearch(supplier);
        List<SupplierItem> supplierItems = supplierItemService.findItems(supplier1);
        for (SupplierItem s : supplierItems) {
            items.add(s.getItem());
        }
        List<Ledger> ledgers = null;
        if (ledgerService.findBySupplier(supplier1) != null) {
            ledgers = ledgerService.findBySupplier(supplier1);
        }

        model.addAttribute("addStatus", true);
        model.addAttribute("ledgers",ledgers );
        model.addAttribute("items", items);
        model.addAttribute("purchaseOrderStatus", PurchaseOrderStatus.values());
        model.addAttribute("searchArea", false);
        model.addAttribute("purchaseOrder", new PurchaseOrder());
        return "purchaseOrder/addPurchaseOrder";*/

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String deletePurchaseOrder(@PathVariable Integer id) {
        purchaseOrderService.delete(id);
        return "redirect:/purchaseOrder";
    }
}
