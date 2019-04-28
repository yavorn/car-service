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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Controller
@RequestMapping("/customer")
public class CustomerController {


    private CustomerService customerService;
    private CarEventService carEventService;
    private ProcedureVisitService procedureVisitService;


    public CustomerController(CustomerService customerService
                            ,CarEventService carEventService
                            ,ProcedureVisitService procedureVisitService) {
        this.customerService = customerService;
        this.carEventService = carEventService;
        this.procedureVisitService = procedureVisitService;
    }


    @GetMapping("/car")
    public String listCustomersCars(Model model){
        model.addAttribute("customersCars", customerService.getAllCustomerCars());
        return "list-customers-cars";
    }


    @GetMapping("/car/{id}")
    public String customerCarByID(Model model, @PathVariable Long id){

        CustomerCars customerCar = customerService.getCustomerCarById(id);
        model.addAttribute("customerCar", customerCar);
        model.addAttribute("listCustomerCarEvents", carEventService.getCarEventByCustomerCarID(id));
        model.addAttribute("listProcedureVisit", procedureVisitService.getAllProcedureVisitsByCarEventCustomerCarID(id));

        return "customer-car";
    }
}
