package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegistrationController {
    private UserDetailsManager userDetailsManager;
    private CustomerService customerService;

    @Autowired
    public RegistrationController(UserDetailsManager userDetailsManager, CustomerService customerService) {
        this.userDetailsManager = userDetailsManager;
        this.customerService = customerService;
    }

    @GetMapping("/register-customer")
    public String showRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "register-customer";
    }

    @PostMapping("/register-customer")
    public String registerNewCustomer(@ModelAttribute CustomerDto customerDto) throws UsernameExistsException {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        customerService.addCustomer(customerDto, authorities);
        return "register-confirmation";
    }

    @GetMapping("register-admin")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new Customer());
        return "register-admin";
    }

    @PostMapping("/register-admin")
    public String registerAdministrator(@ModelAttribute CustomerDto customerDto) throws UsernameExistsException {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        customerService.addAdmin(customerDto, authorities);
        return "register-confirmation";
    }
}
