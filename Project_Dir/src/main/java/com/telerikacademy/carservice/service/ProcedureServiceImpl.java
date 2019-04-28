package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureServiceImpl(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public List<Procedure> getAllProcedures() {
        try {
            return procedureRepository.findAllByProcedureDeletedIsFalse();
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }


    public Procedure getProcedureByID(Long procedureID) {
        try {
            return procedureRepository.findProcedureByProcedureID(procedureID);
        } catch (HibernateException he) {
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

    public void addProcedure(Procedure procedure) {
        try {
            procedureRepository.save(procedure);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }
    }


    public void deleteProcedure(Long procedureID) {
        try {
            //procedureRepository.deleteById(procedureID);
            //Procedure procedureToDelete = procedureRepository.getOne(procedureID);

            Procedure procedureToDelete = procedureRepository.findProcedureByProcedureID(procedureID);

            if(procedureToDelete == null){
                throw new DatabaseItemNotFoundException("Procedure %d is not found", procedureID);
            }

            if(procedureToDelete.isProcedureDeleted()){
                throw new DatabaseItemAlreadyDeletedException("Procedure", procedureID);
            }

            procedureToDelete.setProcedureDeleted();
            procedureRepository.save(procedureToDelete);


        } catch (DatabaseItemNotFoundException | DatabaseItemAlreadyDeletedException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    public void editProcedure(Procedure procedure) {
        try {
            procedureRepository.save(procedure);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
        }
    }


}
