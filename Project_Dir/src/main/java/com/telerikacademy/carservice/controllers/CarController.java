package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.CarService;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
@Api(value = "Cars management system", description = "Contains methods for managing cars in the service.")
public class CarController {

    private CarService carService;
    private CustomerService customerService;


    public CarController(CarService carService, CustomerService customerService) {
        this.carService = carService;
        this.customerService = customerService;
    }

    @ApiOperation(value = "Returns the details of a car.")
    @GetMapping("/model/{id}")
    public String modelDetails(Model model, @PathVariable Long id) {
            Models carModel = carService.getModelById(id);
            model.addAttribute("modelOfCar", carModel);
            return "car";
    }

    @ApiOperation(value = "Returns a list of all customer cars.")
    @GetMapping
    public String listCustomerCars(Model model){
        model.addAttribute("allCustomers", customerService.getAllCustomers());
        model.addAttribute("allCustomersCars", customerService.getAllCustomerCars());
        model.addAttribute("make", new Make());
        model.addAttribute("car", new Models());
        model.addAttribute("allMakes", carService.getAllMakes());
        return "cars";
    }

    @ApiOperation(value = "Returns all models from a certain make.")
    @GetMapping("/models-from-make/{id}")
    public String listModelsFromMake(Model model, @PathVariable Long id) {
        model.addAttribute("modelsFromMake", carService.findModelsByMakeID(id));
        return "list-models-from-make";

    }

    @ApiOperation(value = "Returns the form where car make is added in the database.")
    @GetMapping("/add-make")
    public String addMakeForm(Model model) {

        model.addAttribute("make", new Make());
        return "add-make";
    }

    @ApiOperation(value = "Returns the form where car make is added in the database.")
    @PostMapping("/add-make")
    public String addMake(@Valid @ModelAttribute Make make, BindingResult bindingErrors) {

        if (bindingErrors.hasErrors()) {
            return "add-make";
        }
        carService.addMake(make);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where car make is edited in the database.")
    @GetMapping("/edit-make/{id}")
    public String getEditMake(@PathVariable Long id, Model model) {
        Make make = carService.getMakeById(id);
        model.addAttribute("editMake", make);

        return "edit-make";
    }

    @ApiOperation(value = "Returns the form where car make is edited in the database.")
    @PostMapping("/edit-make/{id}")
    public String editMake(@ModelAttribute Make make, @PathVariable Long id) {
        carService.editMake(id, make);

        return "redirect:/cars";

    }

    @ApiOperation(value = "Returns the form where car model is added in the database.")
    @GetMapping("/add-car")
    public String addModelForm(Model model) {

        model.addAttribute("car", new Models());
        model.addAttribute("allMakes", carService.getAllMakes());
        return "add-car";
    }

    @ApiOperation(value = "Returns the form where car model is added in the database.")
    @PostMapping("/add-car")
    public String addModel(@Valid @ModelAttribute Models model, BindingResult bindingErrors) {

        if (bindingErrors.hasErrors()) {
            return "add-car";
        }
        carService.addModel(model);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where car model is edited in the database.")
    @GetMapping("/edit-carModel/{id}")
    public String getEditModel(@PathVariable Long id, Model model) {
        Models carModel = carService.getModelById(id);
        model.addAttribute("editCarModel", carModel);
        model.addAttribute("allMakes", carService.getAllMakes());

        return "edit-carModel";
    }


    @ApiOperation(value = "Returns the form where car model is edited in the database.")
    @PostMapping("/edit-carModel/{id}")
    public String editModel(@ModelAttribute Models carModel, @PathVariable Long id) {
        carService.editModel(id, carModel);

        return "redirect:/cars";

    }

    @ApiOperation(value = "Returns the form where car make is deleted in the database.")
    @GetMapping("/delete_make/{id}")
    public String deleteMakeByID(@PathVariable Long id) {
        carService.deleteAllModelsByMakeID(carService.findModelsByMakeID(id));
        carService.deleteCarMakeByID(id);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where car make is un-deleted in the database.")
    @GetMapping("/undelete_make/{id}")
    public String undeleteMakeByID(@PathVariable Long id) {
        carService.undeleteCarMakeByID(id);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where car model is deleted in the database.")
    @GetMapping("/delete_model/{id}")
    public String deleteModelByID(@PathVariable Long id) {
        carService.deleteCarModelByID(id);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where car model is un-deleted in the database.")
    @GetMapping("/undelete_model/{id}")
    public String undeleteModelByID(@PathVariable Long id) {
        carService.undeleteCarModelByID(id);
        return "redirect:/cars";
    }

    @ApiOperation(value = "Returns the form where all car models from a specific make are deleted in the database.")
    @GetMapping("/undelete_modelsByMakeId/{id}")
    public String undeleteModelsByMakeID(@PathVariable Long id) {
        carService.undeleteAllModelsByMakeID(carService.findModelsByMakeID(id));
        return "redirect:/cars";
    }
}
