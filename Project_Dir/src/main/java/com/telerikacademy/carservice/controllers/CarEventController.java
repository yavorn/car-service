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


    public CarEventController(CarEventService carEventService,
                              CustomerService customerService,
                              CustomerCarsService customerCarsService,
                              ProcedureService procedureService   ) {
        this.carEventService = carEventService;
        this.customerService = customerService;
        this.customerCarsService = customerCarsService;
        this.procedureService = procedureService;
    }



    @GetMapping("/add-carevent/{customerCarId}")
    public String addCarEventForm(Model model, @PathVariable Long customerCarId) {

        model.addAttribute("carEvent", new CarEvent());
        model.addAttribute("allProcedures",procedureService.getAllProcedures() );
        model.addAttribute("car",customerCarsService.getCustomerCarById(customerCarId));
        return "add-carevent";
    }
    @PostMapping("/add-carevent/{customerCarId}")
    public String addCarEvent(@Valid @ModelAttribute CarEvent carEvent, BindingResult bindingErrors) {
        if(bindingErrors.hasErrors()) {
            return "add-carevent";
        }
        carEventService.addCarEvent(carEvent);
        return "redirect:/cars";
    }

    @GetMapping("/edit-carevent/{id}")
    public String editCarEventForm(Model model, @PathVariable long id) {

        model.addAttribute("carEvent", carEventService.getCarEventByID(id));
        model.addAttribute("allProcedures",procedureService.getAllProcedures() );
        return "edit-carevent";
    }
    @PostMapping("/edit-carevent/{id}")
    public String editCarEvent(@Valid @ModelAttribute CarEvent carEvent, @PathVariable long id) {

        carEventService.editCarEvent(carEvent, id);
        return "redirect:/cars";
    }

}
