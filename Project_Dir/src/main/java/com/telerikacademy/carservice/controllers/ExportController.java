package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.service.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExportController {
    CustomerService customerService;

    @Autowired
    public ExportController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(Model model) {
        model.addAttribute("users", customerService.getAllCustomers());
        return "";
    }
}
