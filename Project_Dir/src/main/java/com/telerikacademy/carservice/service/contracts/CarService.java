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
    void changeCarMakeStatusByID(Long id);
    void changeCarModelStatusByID(Long id);
//    void deleteMake(Long id);
    void changeStatusAllModelsByMakeID(List<Models> modelsToChangeStatus);
//    void deleteModel(Long id);
    List<Models> findModelsByMakeID(Long id);




}
