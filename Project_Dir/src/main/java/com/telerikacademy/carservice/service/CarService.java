package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;

import java.util.List;

public interface CarService {
    void addMake(Make make);
    void addModel(Models model);

    Models getById(Long id);
    List<Models> getAllModels();
    List<Make> getAllMakes();




}
