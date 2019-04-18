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

@Controller
public class ResetPasswordController {
    private CustomerService customerService;

    @Autowired
    public ResetPasswordController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/reset-password")
    public String showResetPassword(Model model){
        model.addAttribute("customerDto", new CustomerDto());
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute CustomerDto customerDto) throws DatabaseItemNotFoundException {
        customerService.resetPassword(customerDto.getEmail());
        return "reset-password-confirmation";
    }
}
