package lk.excellent.pharamacy_management.asset.item.controller;


import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Category;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Status;
import lk.excellent.pharamacy_management.asset.commonAsset.service.SupplierItemService;
import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.item.service.ItemService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final DateTimeAgeService dateTimeAgeService;
    private final UserService userService;
    private final SupplierService supplierService;
    private final SupplierItemService supplierItemService;


    @Autowired
    public ItemController(ItemService itemService, DateTimeAgeService dateTimeAgeService, UserService userService, SupplierService supplierService, SupplierItemService supplierItemService) {
        this.itemService = itemService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.userService = userService;
        this.supplierService = supplierService;
        this.supplierItemService = supplierItemService;
    }

    @RequestMapping
    public String itemPage(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "item/item";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String itemView(@PathVariable("id") Integer id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("suppliers", supplierItemService.findSupplier(item));
        model.addAttribute("itemDetail",item );

        model.addAttribute("addStatus", false);
        return "item/item-detail";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editItemFrom(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("newItem",itemService.findById(id).getCode());
        model.addAttribute("addStatus", false);
        model.addAttribute("category", Category.values());
        model.addAttribute("status", Status.values());
        model.addAttribute("suppliers", supplierService.findAll());
        return "item/addItem";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String itemAddFrom(Model model) {
        try {
            String input = "";
            if (itemService.lastItem() == null) {
                input = "EHS00";
            } else {
                input = itemService.lastItem().getCode();
            }
            String itemNumber= input.replaceAll("[^0-9]+", "");
            Integer ItemNumber = Integer.parseInt(itemNumber);
            int newItemNumber = ItemNumber+1;
            model.addAttribute("addStatus", true);
            model.addAttribute("lastItem",input);
            model.addAttribute("newItem","EHS"+ newItemNumber);
            model.addAttribute("category", Category.values());
            model.addAttribute("status", Status.values());
            model.addAttribute("supplier", supplierService.findAll());
            model.addAttribute("item", new Item());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "item/addItem";
    }

    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search

    @RequestMapping(value = {"/add","/update"}, method = RequestMethod.POST)
    public String addItem(@Valid @ModelAttribute Item item, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.findByUserIdByUserName(auth.getName());
        System.out.println(item);
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("addStatus", true);
            model.addAttribute("category", Category.values());
            model.addAttribute("status", Status.values());
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("item", item);
            return "/item/addItem";
        }
        /*if(item.getId() != null){
            item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
            itemService.persist(item);
            return "redirect:/item";
        }*/
        item.setCreatedAt(dateTimeAgeService.getCurrentDate());
        item.setUpdatedAt(dateTimeAgeService.getCurrentDate());
        itemService.persist(item);
        return "redirect:/item";
    }


    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String removeItem(@PathVariable Integer id) {
        itemService.delete(id);
        return "redirect:/item";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, Item item) {
        model.addAttribute("itemDetail", itemService.search(item));
        return "item/item-detail";
    }

}
