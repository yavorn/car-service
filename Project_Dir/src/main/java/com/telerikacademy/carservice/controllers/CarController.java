package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/model/{id}")
    public String modelDetails(Model model, @PathVariable Long id){

        Models carModel = carService.getById(id);
        model.addAttribute("modelOfCar", carModel);
        return "car";
    }

    @GetMapping
    public String listCars(Model model){
        model.addAttribute("carModels", carService.getAllModels());
        return "list-cars";

    }

    @GetMapping("/add-make")
    public String addMakeForm(Model model) {

        model.addAttribute("make", new Make());
        return "add-make";
    }
    @PostMapping("/add-make")
    public String addMake(@Valid @ModelAttribute Make make , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "add-make";
        }
        carService.addMake(make);
        return "redirect:/cars";
    }


    @GetMapping("/add-car")
    public String addModelForm(Model model) {

        model.addAttribute("car", new Models());
        model.addAttribute("allMakes", carService.getAllMakes());
        return "add-car";
    }
    @PostMapping("/add-car")
    public String addModel(@Valid @ModelAttribute Models models , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "add-car";
        }
        carService.addModel(models);
        return "redirect:/cars";
    }
}
