package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.User;
import com.telerikacademy.carservice.models.contracts.CustomerService;
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
    public String registerNewCustomer(@ModelAttribute Customer customer) throws UsernameExistsException {
        customerService.registerNewUserAccount(customer);
        return "register-confirmation";
    }

    @GetMapping("register-admin")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new User());
        return "register-admin";
    }

    @PostMapping("/register-admin")
    public String registerAdministrator(@ModelAttribute User user) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER", "ADMIN");
        return getUser(user, authorities);
    }

    private String getUser(@ModelAttribute User user, List<GrantedAuthority> authorities) {
        org.springframework.security.core.userdetails.User newUser = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                "{noop}" + user.getPassword(),
                authorities);
        userDetailsManager.createUser(newUser);
        return "register-confirmation";
    }
}
