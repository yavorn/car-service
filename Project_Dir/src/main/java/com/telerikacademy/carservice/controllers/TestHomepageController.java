package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.service.contracts.CarService;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestHomepageController {
    private ProcedureService procedureService;
    private CustomerService customerService;
    private CarService carService;

    @Autowired
    public TestHomepageController(ProcedureService procedureService,
                                  CustomerService customerService,
                                  CarService carService) {
        this.procedureService = procedureService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @GetMapping("/test")
    public String showTestHomePage(Model model){
        model.addAttribute("procedures", procedureService.getAllProcedures());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customerCars", customerService.getAllCustomerCars());

        return "test";
    }
}
