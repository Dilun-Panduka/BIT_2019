package lk.excellent.pharamacy_management.asset.process.reports;

import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.finance.service.InvoiceService;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.excellent.pharamacy_management.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final InvoiceService invoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final LedgerService ledgerService;
    private final UserService userService;
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final ItemService itemService;


    @Autowired
    public ReportController(InvoiceService invoiceService, PurchaseOrderService purchaseOrderService, LedgerService ledgerService, UserService userService, GoodReceivingManagementService goodReceivingManagementService, ItemService itemService) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = purchaseOrderService;
        this.ledgerService = ledgerService;
        this.userService = userService;
        this.goodReceivingManagementService = goodReceivingManagementService;
        this.itemService = itemService;
    }

//    Daily Transaction Report

    public String dailyReport(){
        return "";
    }

//    Date Difference Transaction Report
    public String RangeReport(){
        return "";
    }
}
