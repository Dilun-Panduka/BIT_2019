package lk.excellent.pharamacy_management.asset.customer.controller;

import lk.excellent.pharamacy_management.asset.customer.entity.Customer;
import lk.excellent.pharamacy_management.asset.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restCustomer")
public class CustomerRestController {
    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/{mobile}")
    public List<Customer> searchCustomer(@PathVariable String mobile){
        Customer customer = new Customer();
        customer.setMobile(mobile);
        return customerService.search(customer);
    }
}
