package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/customers")
@Api(value = "Customers management system", description = "Contains the methods for managing customer cars in the service.")
public class CustomerController {
    private CarService carService;
    private CustomerService customerService;
    private CarEventService carEventService;
    private CustomerCarsService customerCarsService;

    public CustomerController(CustomerService customerService,
                              CarEventService carEventService,
                              CustomerCarsService customerCarsService,
                              CarService carService) {
        this.customerService = customerService;
        this.carEventService = carEventService;
        this.customerCarsService = customerCarsService;
        this.carService = carService;
    }

    @ApiOperation(value = "Returns the form where all customers and their cars are listed.")
    @GetMapping
    public String listAllCustomersAndTheirCars(Model model) {
        model.addAttribute("allCustomers", customerService.getAllCustomers());
        model.addAttribute("allCustomersCars", customerService.getAllCustomerCars());
        model.addAttribute("allMakes", carService.getAllMakes());
        model.addAttribute("allModels", carService.getAllModels());
        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("customer", new Customer());
        model.addAttribute("customerCar", new CustomerCars());
        return "customers";
    }

    @ApiOperation(value = "Returns a list of cars for a certain customer.")
    @GetMapping ("/{id}")
    @ResponseBody
    public List<CustomerCars> listCustomerCarsByID(@PathVariable Long id){
        return customerCarsService.getAllCustomerCarsByCustomerId(id);
    }

    @ApiOperation(value = "Returns a list of all cars for all customers.")
    @GetMapping("/car")
    public String listCustomersCars(Model model) {
        model.addAttribute("customersCars", customerService.getAllCustomerCars());
        return "list-customers-cars";
    }

    @ApiOperation(value = "Returns a certain car.")
    @GetMapping("/car/{id}")
    public String customerCarByID(Model model, @PathVariable long id) {

        CustomerCars customerCar = customerService.getCustomerCarById(id);
        model.addAttribute("customerCar", customerCar);
        model.addAttribute("listCustomerCarEvents", carEventService.getCarEventsByCustomerCarID(id));

        return "customer-car";
    }

    @GetMapping("/new-customer-car")
    public String showNewCarPage(Model model) {

        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("customerCar", new CustomerCars());

        return "new-customer-car";
    }

    @PostMapping("/new-customer-car")
    public String addCustomerCar(@ModelAttribute CustomerCars customerCar, String email){
        customerService.createCustomerCar(customerCar, email);
        return "redirect:/customers/car";
    }


}
