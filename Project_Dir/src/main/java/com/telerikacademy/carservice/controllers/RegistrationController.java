package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(value = "Users registration management", description = "Methods that manage the registration of customers and administrators in the car service system.")
public class RegistrationController {
    private CustomerService customerService;

    @Autowired
    public RegistrationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Shows the form for registration of new users.")
    @GetMapping("/customer")
    public String showRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer";
    }

    @ApiOperation(value = "Method that creates a new customer registration.")
    @PostMapping("/customer")
    public String registerNewCustomer(@ModelAttribute CustomerDto customerDto) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        customerService.addCustomer(customerDto, authorities);
        return "redirect:customers";
    }

    @ApiOperation(value = "Method that disables user access to the system.")
    @DeleteMapping("/customer")
    public String disableCustomer(@ModelAttribute CustomerDto customerDto) {
        customerService.disableCustomer(customerDto);
        return "redirect:customers";
    }

    @ApiOperation(value = "Method that enables user access to the system.")
    @PutMapping("/customer")
    public String enableCustomer(@ModelAttribute CustomerDto customerDto) {
        customerService.enableCustomer(customerDto);
        return "redirect:admin-portal";
    }

    @ApiOperation(value = "Shows the form for registration of new administrators.")
    @GetMapping("/admin")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new Customer());
        return "admin";
    }

    @ApiOperation(value = "Method that creates a new administrator registration.")
    @PostMapping("/admin")
    public String registerAdministrator(@ModelAttribute CustomerDto customerDto) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        customerService.addAdmin(customerDto, authorities);
        return "redirect:customers";
    }
}
