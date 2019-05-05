package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@Api(value = "Password page management", description = "Contains methods that manage the password pages of the website.")
public class PasswordController {
    private CustomerService customerService;

    @Autowired
    public PasswordController(CustomerService customerService){
        this.customerService = customerService;
    }

    @ApiOperation(value = "Shows the page where users can change or reset their password.")
    @GetMapping("/password")
    public String showChangeOrResetPassword(Model model){
        model.addAttribute("customerDto", new CustomerDto());
        return "password";
    }
@ApiOperation(value = "Shows the form where users reset their password.")
@PostMapping("/password")
    public String resetPassword(@ModelAttribute CustomerDto customerDto) {
        customerService.resetPassword(customerDto.getEmail());
        return "password-confirmation";
    }

    @ApiOperation(value = "Shows the page where users can change their password.")
    @PutMapping("/password")
    public String changePassword(@ModelAttribute CustomerDto customerDto, Model model) {
        if (!customerDto.getPassword().equals(customerDto.getPasswordConfirmation())) {
            model.addAttribute("error", "Passwords do not match!");
            return "password";
        }
        customerService.changePassword(customerDto);
        return "password-confirmation";
    }
}
