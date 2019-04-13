package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.CarService;
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
@RequestMapping("/car")
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

}
