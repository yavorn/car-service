package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PasswordController {
    private CustomerService customerService;

    @Autowired
    public PasswordController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/password")
    public String showChangeOrResetPassword(Model model){
        model.addAttribute("customerDto", new CustomerDto());
        return "password";
    }

    @PostMapping("/password")
    public String resetPassword(@ModelAttribute CustomerDto customerDto) throws DatabaseItemNotFoundException {
        customerService.resetPassword(customerDto.getEmail());
        return "password-confirmation";
    }

    @PutMapping("/password")
    public String changePassword(@ModelAttribute CustomerDto customerDto, Model model) throws DatabaseItemNotFoundException {
        if (!customerDto.getPassword().equals(customerDto.getPasswordConfirmation())) {
            model.addAttribute("error", "Passwords do not match!");
            return "password";
        }
        customerService.changePassword(customerDto);
        return "password-confirmation";
    }
}
