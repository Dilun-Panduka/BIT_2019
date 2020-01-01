package lk.excellent.pharamacy_management.asset.process.reports;

import lk.excellent.pharamacy_management.asset.customer.entity.Customer;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Enum.PaymentMethod;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Invoice;
import lk.excellent.pharamacy_management.asset.process.finance.service.InvoiceService;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.service.GoodReceivingManagementService;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.service.PurchaseOrderService;
import lk.excellent.pharamacy_management.asset.process.reports.entityHelp.ReportHelp;
import lk.excellent.pharamacy_management.security.entity.User;
import lk.excellent.pharamacy_management.security.service.UserService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import lk.excellent.pharamacy_management.util.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/report")
public class ReportController {
    private final InvoiceService invoiceService;
    private final PurchaseOrderService purchaseOrderService;
    private final LedgerService ledgerService;
    private final UserService userService;
    private final GoodReceivingManagementService goodReceivingManagementService;
    private final ItemService itemService;
    private final DateTimeAgeService dateTimeAgeService;
    private final OperatorService operatorService;


    @Autowired
    public ReportController(InvoiceService invoiceService, PurchaseOrderService purchaseOrderService, LedgerService ledgerService, UserService userService, GoodReceivingManagementService goodReceivingManagementService, ItemService itemService, DateTimeAgeService dateTimeAgeService, OperatorService operatorService) {
        this.invoiceService = invoiceService;
        this.purchaseOrderService = purchaseOrderService;
        this.ledgerService = ledgerService;
        this.userService = userService;
        this.goodReceivingManagementService = goodReceivingManagementService;
        this.itemService = itemService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.operatorService = operatorService;
    }

    //common things for summary
    private String commonThing(Model model, User user, List<Invoice> invoices) {
        model.addAttribute("dateObject", new ReportHelp());
        model.addAttribute("date", dateTimeAgeService.getCurrentDateTime());
        model.addAttribute("user", user.getEmployee().getTitle().getTitle() + " " + user.getEmployee().getName());

        //money related        //Total collection
        BigDecimal totalCollection = BigDecimal.ZERO;
        BigDecimal discountedAmount = BigDecimal.ZERO;
        BigDecimal totalCash = BigDecimal.ZERO;
        BigDecimal totalCard = BigDecimal.ZERO;
        if (invoices.size() != 0) {
            for (Invoice invoice : invoices) {
                totalCollection = operatorService.addition(totalCollection, invoice.getTotalPrice());
            }
            for (Invoice invoice : invoices) {
                if (invoice.getDiscountAmount() != null) {
                    discountedAmount = operatorService.addition(discountedAmount, invoice.getDiscountAmount());
                }
            }
            for (Invoice invoice : invoices) {
                if (invoice.getPaymentMethod().equals(PaymentMethod.CASH) && !invoice.getTotalAmount().equals(BigDecimal.ZERO)) {
                    totalCash = operatorService.addition(totalCash, invoice.getTotalAmount());
                }
            }
            for (Invoice invoice : invoices) {
                if (invoice.getPaymentMethod().equals(PaymentMethod.CREDITCARD) && !invoice.getTotalAmount().equals(BigDecimal.ZERO)) {
                    totalCard = operatorService.addition(totalCard, invoice.getTotalAmount());
                }
            }
        }

        model.addAttribute("totalCollection", totalCollection);
        model.addAttribute("discountedAmount", discountedAmount);
        model.addAttribute("totalCash", totalCash);
        model.addAttribute("totalCard", totalCard);
        model.addAttribute("needToDeposit", totalCash);
        //count
        int invoicedCount = invoices.size();
        List<Customer> customers = new ArrayList<>();
        invoices.forEach(x -> customers.add(x.getCustomer()));
        int patientCount = (int) customers.stream().distinct().count();

        model.addAttribute("invoicedCount", invoicedCount);
        model.addAttribute("patientCount", patientCount);

        return "summary/summary";
    }

    //    Daily Transaction Report
    @GetMapping("/form")
    public String dailyReport(Model model) {
        model.addAttribute("givenDate", dateTimeAgeService.getCurrentDate().toString());

        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Invoice> invoices = invoiceService.findByGivenDate(dateTimeAgeService.getCurrentDate(), dateTimeAgeService.getCurrentDate())
                .stream()
                .filter(x -> x.getUser().equals(user))
                .collect(Collectors.toList());
        return commonThing(model, user, invoices);
    }

    //    Date Difference Transaction Report
    @PostMapping("/search")
    public String RangeReport(@Valid @ModelAttribute ReportHelp reportHelp, Model model) {
        User user = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Invoice> invoices = invoiceService.findByGivenDate(reportHelp.getStartDate(), reportHelp.getEndDate());

        model.addAttribute("givenDate", "FROM : " + reportHelp.getStartDate().toString() + "  TO : " + reportHelp.getEndDate().toString());

        return commonThing(model, user, invoices);
    }
}
