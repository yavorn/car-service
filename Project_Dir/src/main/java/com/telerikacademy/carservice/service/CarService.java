package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;

import java.util.List;

public interface CarService {
    Make addMake(Make make);
    Models addModel(Models model);

    Models getById(Long id);
    List<Models> getAllModels();
    List<Make> getAllMakes();
    void deleteMake(Long id);
    void deleteAllModelsByMakeID(List<Models> modelsToDelete);
    void deleteModel(Long id);
    List<Models> findModelsByMakeID(Long id);




}
