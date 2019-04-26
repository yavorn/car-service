package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.service.contracts.CarService;
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

        Models carModel = carService.getModelById(id);
        model.addAttribute("modelOfCar", carModel);
        return "car";
    }

    @GetMapping
    public String listCars(Model model){
        model.addAttribute("carModels", carService.getAllModels());
        return "list-cars";

    }

    @GetMapping("/models-from-make/{id}")
    public String listModelsFromMake(Model model, @PathVariable Long id){
        model.addAttribute("modelsFromMake", carService.findModelsByMakeID(id));
        return "list-models-from-make";

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


    @GetMapping("/edit-make/{id}")
    public String getEditMake(@PathVariable Long id, Model model) {
        Make make = carService.getMakeById(id);
        model.addAttribute("editMake", make);

        return "edit-make";
    }


    @PostMapping("/edit-make/{id}")
    public String editMake(@ModelAttribute Make make, @PathVariable Long id){
        carService.editMake(id, make);

        return "redirect:/cars";

    }





    @GetMapping("/add-car")
    public String addModelForm(Model model) {

        model.addAttribute("car", new Models());
        model.addAttribute("allMakes", carService.getAllMakes());
        return "add-car";
    }
    @PostMapping("/add-car")
    public String addModel(@Valid @ModelAttribute Models model , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "add-car";
        }
        carService.addModel(model);
        return "redirect:/cars";
    }

    @GetMapping("/edit-carModel/{id}")
    public String getEditModel(@PathVariable Long id, Model model) {
        Models carModel = carService.getModelById(id);
        model.addAttribute("editCarModel", carModel);
        model.addAttribute("allMakes", carService.getAllMakes());

        return "edit-carModel";
    }


    @PostMapping("/edit-carModel/{id}")
    public String editModel(@ModelAttribute Models carModel, @PathVariable Long id){
        carService.editModel(id, carModel);

        return "redirect:/cars";

    }

    @GetMapping("/make_changeStatus/{id}")
    public String changeMakeStatusByID (@PathVariable Long id) {
        carService.changeStatusAllModelsByMakeID(carService.findModelsByMakeID(id));
        carService.changeCarMakeStatusByID(id);
        return "redirect:/cars";
    }


    @GetMapping("/model_changeStatus/{id}")
    public String changeModelStatusByID (@PathVariable Long id) {
        carService.changeCarModelStatusByID(id);
        return "redirect:/cars";
    }

}
