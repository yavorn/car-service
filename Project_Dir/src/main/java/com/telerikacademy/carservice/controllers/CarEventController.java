package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.CarEventRepository;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import com.telerikacademy.carservice.service.contracts.CustomerCarsService;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/carevents")
public class CarEventController {
    private CarEventService carEventService;
    private CustomerService customerService;
    private CustomerCarsService customerCarsService;
    private ProcedureService procedureService;


    public CarEventController(CarEventService carEventService,
                              CustomerService customerService,
                              CustomerCarsService customerCarsService,
                              ProcedureService procedureService) {
        this.carEventService = carEventService;
        this.customerService = customerService;
        this.customerCarsService = customerCarsService;
        this.procedureService = procedureService;
    }



    @GetMapping("/add-carevent")
    public String addCarEventForm(Model model) {

        model.addAttribute("carEvent", new CarEvent());
        model.addAttribute("allCustomerCars",customerCarsService.getAllCustomerCars());
        return "add-carevent";
    }
    @PostMapping("/add-carevent")
    public String addCarEvent(@Valid @ModelAttribute CarEvent carEvent, BindingResult bindingErrors) {
        if(bindingErrors.hasErrors()) {
            return "add-carevent";
        }
        carEventService.addCarEvent(carEvent);
        return "redirect:/cars";
    }

}
