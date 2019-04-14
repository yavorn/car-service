package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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
        return "list_cars";

    }

    @GetMapping("/add_make")
    public String addMakeForm(Model model) {

        model.addAttribute("make", new Make());
        return "add_make";
    }
    @PostMapping("/add_make")
    public String addBeer(@Valid @ModelAttribute Make make , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "add_beer";
        }
        carService.addMake(make);
        return "redirect:/cars";
    }
}
