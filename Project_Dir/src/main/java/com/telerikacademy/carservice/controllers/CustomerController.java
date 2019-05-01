package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import com.telerikacademy.carservice.service.contracts.CarService;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/customers")
public class CustomerController {


    private CustomerService customerService;
    private CarEventService carEventService;
    private ProcedureVisitService procedureVisitService;


    public CustomerController(CustomerService customerService
            , CarEventService carEventService
            , ProcedureVisitService procedureVisitService) {
        this.customerService = customerService;
        this.carEventService = carEventService;
        this.procedureVisitService = procedureVisitService;
    }

    @GetMapping
    public String listAllCustomersAndTheirCars(Model model) {
        model.addAttribute("allCustomers", customerService.getAllCustomers());
        return "customers";
    }


    @GetMapping("/car")
    public String listCustomersCars(Model model) {
        model.addAttribute("customersCars", customerService.getAllCustomerCars());
        return "list-customers-cars";
    }


    @GetMapping("/car/{id}")
    public String customerCarByID(Model model, @PathVariable Long id) {

        CustomerCars customerCar = customerService.getCustomerCarById(id);
        model.addAttribute("customerCar", customerCar);
        model.addAttribute("listCustomerCarEvents", carEventService.getCarEventByCustomerCarID(id));
        model.addAttribute("listProcedureVisit", procedureVisitService.getAllProcedureVisitsByCarEventCustomerCarID(id));

        return "customer-car";
    }
}
