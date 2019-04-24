package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExists;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.MakeRepository;
import com.telerikacademy.carservice.repository.ModelsRepository;
import com.telerikacademy.carservice.service.contracts.CarService;
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
    public void addMake(Make make) {


        try {
            List<Make> existingMakes = getAllMakes()
                    .stream()
                    .filter(carMake -> carMake.getMakeName().equals(make.getMakeName()))
                    .collect(Collectors.toList());

            if (existingMakes.size() != 0) {
                throw new DatabaseItemAlreadyExists("Car Make");
            }
             makeRepository.save(make);


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
    public void editMake(Long id, Make newMake) {

        Make makeToUpdate = makeRepository.findMakeByMakeID(id);
        makeToUpdate.setMakeName(newMake.getMakeName());

        makeRepository.save(makeToUpdate);

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
           return   modelsRepository.save(model);

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
    public void editModel(Long id, Models newModel) {
        Models modelToUpdate = modelsRepository.findModelsByModelID(id);
        modelToUpdate.setModelName(newModel.getModelName());
        modelToUpdate.setMake(newModel.getMake());

        modelsRepository.save(modelToUpdate);
    }

    @Override
    public Make getMakeById(Long id) {
        try {
            List<Make> existingMakes = getAllMakes()
                    .stream()
                    .filter(carMake -> carMake.getMakeID().equals(id))
                    .collect(Collectors.toList());

            if (existingMakes.size() == 0) {
                throw new DatabaseItemNotFoundException("Car Make", id);
            }
            return makeRepository.findMakeByMakeID(id);

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
    public Models getModelById(Long id) {

        try {
            List<Models> existingModels = getAllModels()
                    .stream()
                    .filter(carModel -> carModel.getModelID().equals(id))
                    .collect(Collectors.toList());

            if (existingModels.size() == 0) {
                throw new DatabaseItemNotFoundException("Car model", id);
            }
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
            return makeRepository.findAllByMakeNameOrderByMakeNameAsc();

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

//    public void deleteMake(Long id) {
//        Make make =  makeRepository.findMakeByMakeID(id);
////        if (model == null) {
////            throw new ModelNotFoundException(id);
////        }
//       makeRepository.deleteById(id);
//    }

//    @Override
//    public void deleteAllModelsByMakeID(List<Models> modelsToDelete) {
//
//        for (Models model :  modelsToDelete ) {
//
//            modelsRepository.deleteById(model.getModelID());
//        }
//
//    }

//    public void deleteModel(Long id) {
//        Models model =  modelsRepository.findModelsByModelID(id);
////        if (model == null) {
////            throw new ModelNotFoundException(id);
////        }
//        modelsRepository.deleteById(id);
//    }

    @Override
    public List<Models> findModelsByMakeID(Long id) {

        try {
            List<Models> existingModels = getAllModels();

            if (existingModels.size() == 0) {
                throw new DatabaseItemNotFoundException("Car models from Make", id);
            }
            return modelsRepository.findModelsByMake_MakeID(id);

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
}
