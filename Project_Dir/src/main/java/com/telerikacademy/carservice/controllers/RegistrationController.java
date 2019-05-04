package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegistrationController {
    private CustomerService customerService;

    @Autowired
    public RegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public String showRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer";
    }

    @PostMapping("/customer")
    public String registerNewCustomer(@ModelAttribute CustomerDto customerDto) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        customerService.addCustomer(customerDto, authorities);
        return "new-customer-confirmation";
    }

    @DeleteMapping("/customer")
    public String disableCustomer(@ModelAttribute CustomerDto customerDto) {
        customerService.disableCustomer(customerDto);
        return "redirect:customers";
    }

    @PutMapping("/customer")
    public String enableCustomer(@ModelAttribute CustomerDto customerDto) {
        customerService.enableCustomer(customerDto);
        return "redirect:admin-portal";
    }

    @GetMapping("/admin")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new Customer());
        return "admin";
    }

    @PostMapping("/admin")
    public String registerAdministrator(@ModelAttribute CustomerDto customerDto) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        customerService.addAdmin(customerDto, authorities);
        return "new-customer-confirmation";
    }
}
