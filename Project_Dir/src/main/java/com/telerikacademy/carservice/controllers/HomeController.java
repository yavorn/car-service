package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.service.contracts.CustomerCarsService;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(value = "Homepage management", description = "Contains the methods for managing the homepage, admin-portal and access-denied page.")
public class HomeController {
    private CustomerService customerService;
    private CustomerCarsService customerCarsService;

    @Autowired
    public HomeController(CustomerService customerService, CustomerCarsService customerCarsService) {
        this.customerService = customerService;
        this.customerCarsService = customerCarsService;
    }

    @ApiOperation(value = "Returns homepage.")
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @ApiOperation(value = "Returns admin portal.")
    @GetMapping("/admin-portal")
    public String showAdminPortal() {
        return "admin-portal";
    }

    @ApiOperation(value = "Returns customer portal.")
    @GetMapping("/customer-portal")
    public String showCustomerPortal(Model model) {
        model.addAttribute("cars", customerCarsService.getAllCustomerCarsByCustomerEmail(customerService.getLoggedUserEmail()));
        return "customer-portal";
    }

    @ApiOperation(value = "Returns access denied page.")
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
