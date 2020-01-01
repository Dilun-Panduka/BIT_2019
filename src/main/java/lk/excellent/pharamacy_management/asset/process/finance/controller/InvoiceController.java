package lk.excellent.pharamacy_management.asset.process.finance.controller;

import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Gender;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Title;
import lk.excellent.pharamacy_management.asset.customer.controller.CustomerRestController;
import lk.excellent.pharamacy_management.asset.customer.entity.Customer;
import lk.excellent.pharamacy_management.asset.customer.service.CustomerService;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Enum.Frequency;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Enum.PaymentMethod;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Invoice;
import lk.excellent.pharamacy_management.asset.process.finance.entity.InvoiceQuantity;
import lk.excellent.pharamacy_management.asset.process.finance.service.DiscountRatioService;
import lk.excellent.pharamacy_management.asset.process.finance.service.InvoiceService;
import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import lk.excellent.pharamacy_management.security.service.UserService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import lk.excellent.pharamacy_management.util.service.FileHandelService;
import lk.excellent.pharamacy_management.util.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final DiscountRatioService discountRatioService;
    private final DateTimeAgeService dateTimeAgeService;
    private final LedgerService ledgerService;
    private final UserService userService;
    private final OperatorService operatorService;
    private final FileHandelService fileHandelService;
    private final ServletContext context;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, CustomerService customerService, ItemService itemService, DiscountRatioService discountRatioService, DateTimeAgeService dateTimeAgeService, LedgerService ledgerService, UserService userService, OperatorService operatorService, FileHandelService fileHandelService, ServletContext context) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.discountRatioService = discountRatioService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.ledgerService = ledgerService;
        this.userService = userService;
        this.operatorService = operatorService;
        this.fileHandelService = fileHandelService;
        this.context = context;
    }

    @GetMapping("/addForm")
    public String giveForm(Model model) {
        /*Customer relevant details*/
        try {
            String input = "";
            if (customerService.lastCustomer() == null) {
                input = "EHS00";
            } else {
                input = customerService.lastCustomer().getNumber();
            }
            String customerNumber = input.replaceAll("[^0-9]+", "");
            Integer CustomerNumber = Integer.parseInt(customerNumber);
            int newCustomerNumber = CustomerNumber + 1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastCustomer", input);
            model.addAttribute("newCustomer", "EHS" + newCustomerNumber);
            model.addAttribute("title", Title.values());
            model.addAttribute("gender", Gender.values());
            model.addAttribute("customer", new Customer());
            model.addAttribute("invoice", new Invoice());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        /*Medicines all*/
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("frequencies", Frequency.values());

        /*Discounts and Payment Methods*/
        model.addAttribute("discountRatios", discountRatioService.findAll());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        /*customer search related links*/
        model.addAttribute("customerUrl", MvcUriComponentsBuilder
                .fromMethodName(CustomerRestController.class, "searchCustomer", "")
                .build()
                .toString());
        return "invoice/addInvoice";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String addInvoice(@ModelAttribute("invoice") Invoice invoice, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        if (invoice.getCustomer() != null) {
            invoice.setCustomer(customerService.persist(invoice.getCustomer()));
        }
        List<InvoiceQuantity> invoiceqty = invoice.getInvoiceQuantities().stream().
                filter(value -> value.getQuantity() != 0).collect(Collectors.toList());

        invoiceqty.forEach(v -> v.setInvoice(invoice));

        System.out.println(invoiceqty);

        String c = "";
        if (invoiceService.lastInvoice() == null) {
            c = "EHSIN00";
        } else {
            c = invoiceService.lastInvoice().getNumber().toString();
        }
        String invoiceNumber = c.replaceAll("[^0-9]+", "");
        int InNumber = Integer.parseInt(invoiceNumber);
        int newInvoiceNumber = InNumber + 1;
        invoice.setNumber("EHSIN" + newInvoiceNumber);
        invoice.setInvoiceQuantities(invoiceqty);
        invoice.setUser(userService.findById(userId));
        invoice.setInvoicedAt(dateTimeAgeService.getCurrentDate());
        invoice.setDiscountAmount(operatorService.subtraction(invoice.getTotalPrice(),invoice.getTotalAmount()));
        Invoice invoice1 = invoiceService.persist(invoice);
        invoice1.getInvoiceQuantities().forEach(
                x -> {
                    Ledger ledger = ledgerService.findByItem(x.getItem());
                    int availableLeger = ledger.getAvailableQuantity();
                    ledger.setAvailableQuantity(availableLeger - x.getQuantity());
                    ledgerService.persist(ledger);
                    Item item = itemService.findById(x.getItem().getId());
                    int availableItem = item.getSoh();
                    item.setSoh(availableItem - x.getQuantity());
                    itemService.persist(item);
                }
        );
        boolean isFlag = invoiceService.createPdf(invoice, context, request, response);
        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/report/" + "invoices" + ".pdf");
            fileHandelService.filedownload(fullPath, response, "invoices.pdf");
        }
        return "redirect:/addForm";
    }

}
