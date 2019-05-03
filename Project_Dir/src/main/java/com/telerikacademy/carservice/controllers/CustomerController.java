package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {


    private CustomerService customerService;
    private CarEventService carEventService;
    private CustomerCarsService customerCarsService;


    public CustomerController(CustomerService customerService,
                              CarEventService carEventService,
                              CustomerCarsService customerCarsService) {
        this.customerService = customerService;
        this.carEventService = carEventService;
        this.customerCarsService = customerCarsService;
    }

    @GetMapping
    public String listAllCustomersAndTheirCars(Model model) {
        model.addAttribute("allCustomers", customerService.getAllCustomers());
        model.addAttribute("allCustomersCars", customerService.getAllCustomerCars());
        model.addAttribute("customer", new Customer());
        return "customers";
    }

    @GetMapping ("/{id}")
    @ResponseBody
    public List<CustomerCars> listCustomerCarsByID(Model model, @PathVariable Long id){
        return customerCarsService.getAllCustomerCarsByCustomerId(id);
    }

    @GetMapping("/car")
    public String listCustomersCars(Model model) {
        model.addAttribute("customersCars", customerService.getAllCustomerCars());
        return "list-customers-cars";
    }


    @GetMapping("/car/{id}")
    public String customerCarByID(Model model, @PathVariable long id) {

        CustomerCars customerCar = customerService.getCustomerCarById(id);
        model.addAttribute("customerCar", customerCar);
        model.addAttribute("listCustomerCarEvents", carEventService.getCarEventsByCustomerCarID(id));

        return "customer-car";
    }
}
