package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.User;
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

    @Autowired
    public RegistrationController(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute User user) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER");
        return getUser(user, authorities);
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
