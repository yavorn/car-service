package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.*;
import com.telerikacademy.carservice.service.contracts.*;
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
    private ProcedureVisitService procedureVisitService;


    public CarEventController(CarEventService carEventService,
                              CustomerService customerService,
                              CustomerCarsService customerCarsService,
                              ProcedureService procedureService,
                              ProcedureVisitService procedureVisitService    ) {
        this.carEventService = carEventService;
        this.customerService = customerService;
        this.customerCarsService = customerCarsService;
        this.procedureService = procedureService;
        this.procedureVisitService = procedureVisitService;
    }



    @GetMapping("/add-carevent")
    public String addCarEventForm(Model model) {

        model.addAttribute("carEvent", new CarEvent());
        model.addAttribute("allProcedures",procedureService.getAllProcedures() );

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
