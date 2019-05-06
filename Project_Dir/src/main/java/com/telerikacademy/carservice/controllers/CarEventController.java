package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.*;
import com.telerikacademy.carservice.service.contracts.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/carevents")
@Api(value = "Car Events management system", description = "Contains methods for managing car events in the service.")
public class CarEventController {
    private CarEventService carEventService;
    private CustomerCarsService customerCarsService;
    private ProcedureService procedureService;


    public CarEventController(CarEventService carEventService,
                              CustomerCarsService customerCarsService,
                              ProcedureService procedureService   ) {
        this.carEventService = carEventService;
        this.customerCarsService = customerCarsService;
        this.procedureService = procedureService;
    }

    @ApiOperation(value = "Returns the form where a car event is added to a certain car.")
    @GetMapping("/add-carevent/{customerCarId}")
    public String addCarEventForm(Model model, @PathVariable Long customerCarId) {

        model.addAttribute("carEvent", new CarEvent());
        model.addAttribute("allProcedures",procedureService.getAllProcedures() );
        model.addAttribute("car",customerCarsService.getCustomerCarById(customerCarId));
        return "add-carevent";
    }

    @ApiOperation(value = "Returns the form where a car event is added to a certain car.")
    @PostMapping("/add-carevent/{customerCarId}")
    public String addCarEvent(@Valid @ModelAttribute CarEvent carEvent, BindingResult bindingErrors) {
        if(bindingErrors.hasErrors()) {
            return "add-carevent";
        }
        carEventService.addCarEvent(carEvent);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where a certain car event is edited.")
    @GetMapping("/edit-carevent/{id}")
    public String editCarEventForm(Model model, @PathVariable long id) {

        model.addAttribute("carEvent", carEventService.getCarEventByID(id));
        model.addAttribute("allProcedures",procedureService.getAllProcedures() );
        return "edit-carevent";
    }

    @ApiOperation(value = "Returns the form where a certain car event is edited.")
    @PostMapping("/edit-carevent/{id}")
    public String editCarEvent(@Valid @ModelAttribute CarEvent carEvent, @PathVariable long id) {

        carEventService.editCarEvent(carEvent, id);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where customer car event is deleted in the database.")
    @GetMapping("/delete-carevent/{id}")
    public String deleteCarEventByID(@PathVariable long id) {
        carEventService.deleteCarEvent(id);
        return "redirect:/cars";
    }

}
