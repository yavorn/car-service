package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
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

@Service
public class CarServiceImpl implements CarService {

    private static final String MAKE_EXIST_EXCEPTION_MSG = "Car Make with name/ID: %s already exist!";
    private static final String MAKE_DELETED_EXCEPTION_MSG = "Car Make with name/ID: %s already deleted!";
    private static final String MAKE_NOT_DELETED_EXCEPTION_MSG = "Car Make with name/ID: %s is not deleted!";
    private static final String MODEL_EXIST_EXCEPTION_MSG = "Car Model with name/ID: %s already exist!";
    private static final String MODEL_DELETED_EXCEPTION_MSG = "Car Model with name/ID: %s already deleted!";
    private static final String MODEL_NOT_DELETED_EXCEPTION_MSG = "Car Model with name/ID: %s is not deleted!";
    private static final String MAKE_NOT_FOUND_EXCEPTION_MSG = "Car Make with name/ID: %s not found!";
    private static final String MODEL_NOT_FOUND_EXCEPTION_MSG = "Car Model with name/ID: %s not found!";
    private static final String MODEL_NOT_FOUND_BY_MAKE_ID_EXCEPTION_MSG = "Car Model by Make name/ID: %s not found!";



    private ModelsRepository modelsRepository;
    private MakeRepository makeRepository;

    @Autowired
    public CarServiceImpl(ModelsRepository modelsRepository, MakeRepository makeRepository) {
        this.modelsRepository = modelsRepository;
        this.makeRepository = makeRepository;
    }

    @Override
    public void addMake(Make make) {

        Make existingMake = makeRepository.findMakeByMakeName(make.getMakeName());

        if (existingMake != null && existingMake.isMakeDeleted()) {
            throw new DatabaseItemAlreadyDeletedException(String.format(MAKE_DELETED_EXCEPTION_MSG, make.getMakeName()));
        }
        else if(existingMake != null && !existingMake.isMakeDeleted()){
            throw new DatabaseItemAlreadyExists(String.format(MAKE_EXIST_EXCEPTION_MSG, make.getMakeName()));
        }
        makeRepository.save(make);

    }

    @Override
    public void editMake(Long id, Make newMake) {

        Make makeToUpdate = makeRepository.findMakeByMakeID(id);

        if(makeToUpdate == null){
            throw new DatabaseItemNotFoundException(String.format(MAKE_NOT_FOUND_EXCEPTION_MSG, makeToUpdate.getMakeName()));
        }
        makeToUpdate.setMakeName(newMake.getMakeName());

        makeRepository.save(makeToUpdate);

    }

    @Override
    public Models addModel(Models model) {

        Models existingModel = modelsRepository.findModelsByModelName(model.getModelName());

        if (existingModel != null && existingModel.isModelDeleted()) {
            throw new DatabaseItemAlreadyDeletedException(String.format(MODEL_DELETED_EXCEPTION_MSG, model.getModelName()));
        }
        else if(existingModel != null && !existingModel.isModelDeleted()){
            throw new DatabaseItemAlreadyExists(String.format(MODEL_EXIST_EXCEPTION_MSG, model.getModelName()));
        }
        return modelsRepository.save(model);

    }

    @Override
    public void editModel(Long id, Models newModel) {
        Models modelToUpdate = modelsRepository.findModelsByModelID(id);

        if(modelToUpdate == null){
            throw new DatabaseItemNotFoundException(String.format(MODEL_NOT_FOUND_EXCEPTION_MSG, modelToUpdate.getModelName()));
        }
        modelToUpdate.setModelName(newModel.getModelName());
        modelToUpdate.setMake(newModel.getMake());

        modelsRepository.save(modelToUpdate);
    }

    @Override
    public Make getMakeById(Long id) {

            Make make = makeRepository.findMakeByMakeID(id);
            if (make == null) {
                throw new DatabaseItemNotFoundException(String.format(MAKE_NOT_FOUND_EXCEPTION_MSG, id));
            }
            return makeRepository.findMakeByMakeID(id);


    }

    @Override
    public Models getModelById(Long id) {

        Models model = modelsRepository.findModelsByModelID(id);
        if (model == null) {
            throw new DatabaseItemNotFoundException(String.format(MODEL_NOT_FOUND_EXCEPTION_MSG, id));
        }
        return modelsRepository.findModelsByModelID(id);

    }

    @Override
    public List<Make> getAllMakes() {

        try {
            return makeRepository.getAllByMakeDeletedFalseOrderByMakeNameAsc();

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
            return modelsRepository.getAllByModelDeletedFalse();

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }

    @Override
    public void deleteCarMakeByID(Long id) {
        Make make =  makeRepository.findMakeByMakeID(id);
        if (make == null) {
            throw new DatabaseItemNotFoundException(String.format(MAKE_NOT_FOUND_EXCEPTION_MSG, id.toString()));
        }

        if (!make.isMakeDeleted()) {
            make.setMakeDeleted();
        }
        else {
            throw new DatabaseItemAlreadyDeletedException(String.format(MAKE_DELETED_EXCEPTION_MSG, id.toString()));
        }
        makeRepository.save(make);

    }

    @Override
    public void undeleteCarMakeByID(Long id) {
        Make make =  makeRepository.findMakeByMakeID(id);
        if (make == null) {
            throw new DatabaseItemNotFoundException(String.format(MAKE_NOT_FOUND_EXCEPTION_MSG, id.toString()));
        }

        if (make.isMakeDeleted()) {
            make.setMakeUndeleted();
        }
        else {
            throw new DatabaseItemAlreadyDeletedException(String.format(MAKE_NOT_DELETED_EXCEPTION_MSG, id.toString()));
        }
        makeRepository.save(make);

    }
    @Override
    public void deleteCarModelByID(Long id) {
        Models model =  modelsRepository.findModelsByModelID(id);
        if (model == null) {
            throw new DatabaseItemNotFoundException(String.format(MODEL_NOT_FOUND_EXCEPTION_MSG, id.toString()));
        }
        if (!model.isModelDeleted()) {
            model.setModelDeleted();
        }
        else {
            throw new DatabaseItemAlreadyDeletedException(String.format(MODEL_DELETED_EXCEPTION_MSG, id.toString()));
        }
        modelsRepository.save(model);
    }

    @Override
    public void undeleteCarModelByID(Long id) {
        Models model =  modelsRepository.findModelsByModelID(id);
        if (model == null) {
            throw new DatabaseItemNotFoundException(String.format(MODEL_NOT_FOUND_EXCEPTION_MSG, id.toString()));
        }
        if (model.isModelDeleted()) {
            model.setModelUndeleted();
        }
        else {
            throw new DatabaseItemAlreadyDeletedException(String.format(MODEL_NOT_DELETED_EXCEPTION_MSG, id.toString()));
        }
        modelsRepository.save(model);
    }

    @Override
    public void deleteAllModelsByMakeID(List<Models> modelsToDelete) {

        for (Models model :  modelsToDelete ) {

            if (!model.isModelDeleted()) {
                model.setModelDeleted();
            }
            else {
               continue;
            }
            modelsRepository.save(model);
        }

    }

    @Override
    public void undeleteAllModelsByMakeID(List<Models> modelsToUndelete) {

        for (Models model :  modelsToUndelete ) {

            if (model.isModelDeleted()) {
                model.setModelUndeleted();
            }
            else {
                continue;
            }
            modelsRepository.save(model);
        }

    }


    @Override
    public List<Models> findModelsByMakeID(Long id) {

        List<Models> existingModels = modelsRepository.findModelsByMake_MakeID(id);

        if (existingModels.size() == 0) {
            throw new DatabaseItemNotFoundException(String.format(MODEL_NOT_FOUND_BY_MAKE_ID_EXCEPTION_MSG, id.toString()));
        }
        return modelsRepository.findModelsByMake_MakeID(id);

    }


}
