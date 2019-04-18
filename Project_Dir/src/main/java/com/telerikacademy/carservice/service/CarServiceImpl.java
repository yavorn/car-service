package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExists;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.MakeRepository;
import com.telerikacademy.carservice.repository.ModelsRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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


        try {
            List<Make> existingMakes = getAllMakes()
                    .stream()
                    .filter(carMake -> carMake.getMakeName().equals(make.getMakeName()))
                    .collect(Collectors.toList());

            if (existingMakes.size() != 0) {
                throw new DatabaseItemAlreadyExists("Car Make");
            }
            return makeRepository.save(make);


        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        } catch (DatabaseItemAlreadyExists e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    @Override
    public Models addModel(Models model) {

        try {
            List<Models> existingModels = getAllModels()
                    .stream()
                    .filter(carModel -> carModel.getModelName().equals(model.getModelName()))
                    .collect(Collectors.toList());

            if (existingModels.size() != 0) {
                throw new DatabaseItemAlreadyExists("Car Model");
            }
            return modelsRepository.save(model);

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        } catch (DatabaseItemAlreadyExists e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    @Override
    public Models getById(Long id) {

        try {
            return modelsRepository.findModelsByModelID(id);

        }  catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );

        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }
    @Override
    public List<Make> getAllMakes() {

        try {
            return makeRepository.findAll();

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }

    @Override
    public List<Models> getAllModels() {



        try {
            return modelsRepository.findAll();

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }

    public void deleteMake(Long id) {
        Make make =  makeRepository.findMakeByMakeID(id);
//        if (model == null) {
//            throw new ModelNotFoundException(id);
//        }
       makeRepository.deleteById(id);
    }

    @Override
    public void deleteAllModelsByMakeID(List<Models> modelsToDelete) {

        for (Models model :  modelsToDelete ) {

            modelsRepository.deleteById(model.getModelID());
        }

    }

    public void deleteModel(Long id) {
        Models model =  modelsRepository.findModelsByModelID(id);
//        if (model == null) {
//            throw new ModelNotFoundException(id);
//        }
        modelsRepository.deleteById(id);
    }

    @Override
    public List<Models> findModelsByMakeID(Long id) {
        return modelsRepository.findModelsByMake_MakeID(id);
    }
}
