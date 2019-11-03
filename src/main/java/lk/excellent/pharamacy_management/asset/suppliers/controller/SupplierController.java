package lk.excellent.pharamacy_management.asset.suppliers.controller;


import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Gender;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Title;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lk.excellent.pharamacy_management.asset.suppliers.service.SupplierService;
import lk.excellent.pharamacy_management.security.service.UserService;
import lk.excellent.pharamacy_management.util.service.DateTimeAgeService;
import lk.excellent.pharamacy_management.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    private final SupplierService supplierService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final EmailService emailService;


    @Autowired
    public SupplierController(SupplierService supplierService, DateTimeAgeService dateTimeAgeService, UserService userService, EmailService emailService) {
        this.supplierService = supplierService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping
    public String supplierPage(Model model) {
        List<Supplier> suppliers = supplierService.findAll();
        model.addAttribute("suppliers", suppliers);
        return "supplier/supplier";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String supplierView(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("supplierDetail", supplierService.findById(id));
        return "supplier/supplier-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSupplierFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        model.addAttribute("newSupplier",supplierService.findById(id).getCode());
        model.addAttribute("addStatus", false);
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        return "supplier/addSupplier";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String supplierAddFrom(Model model) {
        try {
            String input;
            if (supplierService.lastSupplier().getCode().equals(null)) {
                input = "000";
            } else {
                input = supplierService.lastSupplier().getCode();
            }
            String supplierCode= input.replaceAll("[^0-9]+", "");
            Integer SupplierCode = Integer.parseInt(supplierCode);
            int newSupplierCode = SupplierCode+1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastSupplier",input);
            model.addAttribute("newSupplier","EHS"+ newSupplierCode);
            model.addAttribute("title", Title.values());
            Model gender = model.addAttribute("gender", Gender.values());
            model.addAttribute("supplier", new Supplier());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "supplier/addSupplier";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addSupplier(@Valid @ModelAttribute Supplier supplier, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        System.out.println(supplier);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("supplier", supplier);
            return "/supplier/addSupplier";
        }
        if (supplier.getId() != null){
            supplier.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            if(supplier.getEmail() != null){
                String message = "Welcome to Excellent Health Solution \n " +
                        "Your detail is updated"+
                        "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                        "\n Kindly request keep your data up to date with us." +
                        "\n \n \n   Thank You" +
                        "\n Excellent Health Solution";

                    redirectAttributes.addFlashAttribute("message", "Successfully Updated.");
                    supplierService.persist(supplier);
                    return "redirect:/supplier";

            }

            supplierService.persist(supplier);
            return "redirect:/supplier";
        }
        if (supplier.getEmail() != null){
            String message = "Welcome to Excellent Health Solution \n " +
                    "Your registration number is "+supplier.getLand()+
                    "\nYour Details are"+
                    "\n "+supplier.getCode()+
                    "\n "+supplier.getName()+
                    "\n "+supplier.getAddress()+
                    "\n "+supplier.getEmail()+
                    "\n "+supplier.getLand()+
                    "\n "+supplier.getContactName()+
                    "\n "+supplier.getContactMobile()+
                    "\n "+supplier.getContactEmail()+
                    "\n\n\n\n\n Please inform us to if there is any changes on your details" +
                    "\n Kindly request keep your data up to date with us. so we can provide better service for you." +
                    "\n \n \n   Thank You" +
                    "\n Excellent Health Solution";
            boolean isFlag = emailService.sendSupplierRegistrationEmail(supplier.getEmail(),"Welcome to Excellent Health Solution ", message);
            if (isFlag){
                redirectAttributes.addFlashAttribute("message", "Successfully Add and Email was sent.");
                redirectAttributes.addFlashAttribute("alertStatus",true);
                supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());
                supplierService.persist(supplier);
                return "redirect:/supplier";
            }else {
                redirectAttributes.addFlashAttribute("message", "Successfully Add but Email was not sent.");
                redirectAttributes.addFlashAttribute("alertStatus",false);
                supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());
                supplierService.persist(supplier);
                return "redirect:/supplier";
            }
        }
        supplier.setCreatedAt(dateTimeAgeService.getCurrentDate());
        supplierService.persist(supplier);
        return "redirect:/supplier";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeSupplier(@PathVariable Integer id) {
        supplierService.delete(id);
        return "redirect:/supplier";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Supplier supplier) {
        model.addAttribute("supplierDetail", supplierService.search(supplier));
        return "supplier/supplier-detail";
    }

}
