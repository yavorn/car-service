package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;

import java.util.List;

public interface CarService {
    void addMake(Make make);
    void editMake(Long id, Make make);
    Models addModel(Models model);
    void editModel(Long id, Models model);

    Make getMakeById(Long id);
    Models getModelById(Long id);
    List<Models> getAllModels();
    List<Make> getAllMakes();
    void deleteCarMakeByID(Long id);
    void undeleteCarMakeByID(Long id);
    void deleteCarModelByID(Long id);
    void undeleteCarModelByID(Long id);
    void deleteAllModelsByMakeID(List<Models> modelsToDelete);
    void undeleteAllModelsByMakeID(List<Models> modelsToUndelete);
    List<Models> findModelsByMakeID(Long id);




}
