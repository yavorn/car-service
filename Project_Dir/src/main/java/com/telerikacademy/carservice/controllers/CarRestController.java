package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;


@RestController
@RequestMapping("/api")
public class CarRestController {
private CarService carService;

@Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/makes")
    public List<Make> getAllMakes() {
        return carService.getAllMakes();
    }

    @GetMapping("/models")
    public List<Models> getAllModels() {
        return carService.getAllModels();
    }



    @GetMapping("/model/{id}")
    public Models getModelByID(@PathVariable Long id){
        Models model = carService.getById(id);
        return model;
    }

    @PostMapping("/addNewCar")
    public Models addNewModel(@RequestBody Models model){
        return carService.addModel(model);
    }



}
