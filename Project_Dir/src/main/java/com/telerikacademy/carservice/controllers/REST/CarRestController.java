package com.telerikacademy.carservice.controllers.REST;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        Models model = carService.getModelById(id);
        return model;
    }

    @PostMapping("/addNewCar")
    public Models addNewModel(@RequestBody Models model){
         return carService.addModel(model);
    }



}
