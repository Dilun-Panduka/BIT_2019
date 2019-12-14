package lk.excellent.pharamacy_management.asset.process.finance.controller;

import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Gender;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Title;
import lk.excellent.pharamacy_management.asset.customer.controller.CustomerRestController;
import lk.excellent.pharamacy_management.asset.customer.entity.Customer;
import lk.excellent.pharamacy_management.asset.customer.service.CustomerService;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Enum.Frequency;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Enum.PaymentMethod;
import lk.excellent.pharamacy_management.asset.process.finance.service.DiscountRatioService;
import lk.excellent.pharamacy_management.asset.process.finance.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ItemService itemService;
    private final DiscountRatioService discountRatioService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, CustomerService customerService, ItemService itemService, DiscountRatioService discountRatioService) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.itemService = itemService;
        this.discountRatioService = discountRatioService;
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
            Model gender = model.addAttribute("gender", Gender.values());
            model.addAttribute("customer", new Customer());
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
}
