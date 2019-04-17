package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.MakeRepository;
import com.telerikacademy.carservice.repository.ModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private ModelsRepository modelsRepository;
    private MakeRepository makeRepository;

    @Autowired
    public CarServiceImpl(ModelsRepository modelsRepository, MakeRepository makeRepository) {
        this.modelsRepository = modelsRepository;
        this.makeRepository = makeRepository;
    }

    @Override
    public Make addMake(Make make) {

        return makeRepository.save(make);
    }

    @Override
    public Models addModel(Models model) {

        return modelsRepository.save(model);
    }

    @Override
    public Models getById(Long id) {

        Models model;

        model  = modelsRepository.findModelsByModelID(id);

        return model ;
    }
    @Override
    public List<Make> getAllMakes() {

        return makeRepository.findAll();
    }

    @Override
    public List<Models> getAllModels() {

        return modelsRepository.findAll();
    }

    public void deleteMake(Long id) {
        Make make =  makeRepository.findMakeByMakeID(id);
//        if (model == null) {
//            throw new ModelNotFoundException(id);
//        }
       makeRepository.deleteById(id);
    }

    @Override
    public void deleteAllByMake_MakeID(Long id) {
        modelsRepository.deleteModelsByMake_MakeID(id);
    }

    public void deleteModel(Long id) {
        Models model =  modelsRepository.findModelsByModelID(id);
//        if (model == null) {
//            throw new ModelNotFoundException(id);
//        }
        modelsRepository.deleteById(id);
    }
}
