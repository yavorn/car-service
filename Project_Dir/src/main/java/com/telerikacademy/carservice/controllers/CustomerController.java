package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.CarEventService;
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

    //private CarService carService;
    private CustomerService customerService;
   // private CarEventService carEventService;
    //private ProcedureVisitService procedureVisitService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/car/{id}")
    public String customerCarDetails(Model model, @PathVariable Long id){

        CustomerCars customerCarr = customerService.getCustomerCarById(id);
        model.addAttribute("customerCar", customerCarr);
        return "customer-car";
    }
}
