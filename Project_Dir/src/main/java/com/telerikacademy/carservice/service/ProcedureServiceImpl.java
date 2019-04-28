package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExists;
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
            Procedure procedureToFind = procedureRepository.findProcedureByProcedureID(procedureID);

            if (procedureToFind == null) {
                throw new DatabaseItemNotFoundException("Procedure %d is not found", procedureID);
            }

            return procedureToFind;

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
            if (procedureRepository.findProcedureByProcedureName(procedure.getProcedureName()) == procedure){
                throw new DatabaseItemAlreadyExists("Procedure");
            }
            procedureRepository.save(procedure);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseItemAlreadyExists("Procedure");
        }
    }


    public void deleteProcedure(Long procedureID) {
        try {

            Procedure procedureToDelete = procedureRepository.findProcedureByProcedureID(procedureID);

            if (procedureToDelete == null) {
                throw new DatabaseItemNotFoundException("Procedure %d is not found", procedureID);
            }

            if (procedureToDelete.isProcedureDeleted()) {
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

    public void editProcedure(Procedure procedure, Long id) {
        Procedure procedureToUpdate = procedureRepository.findProcedureByProcedureID(id);
        procedureToUpdate.setProcedureName(procedure.getProcedureName());
        procedureToUpdate.setProcedurePrice(procedure.getProcedurePrice());

        procedureRepository.save(procedureToUpdate);
    }


}
