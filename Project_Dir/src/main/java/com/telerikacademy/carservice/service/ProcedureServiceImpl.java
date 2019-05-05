package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExistsException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    private static final String PROCEDURE_DELETED_EXCEPTION_MSG = "Procedure with name: %s already deleted!";
    private static final String PROCEDURE_EXIST_EXCEPTION_MSG = "Procedure with name: %s already exist!";
    private static final String PROCEDURE_NOT_FOUND_MSG = "Procedure with id: %d not found!";
    private static final String PROCEDURE_WITH_ID_IS_ALREADY_DELETED = "Procedure with id %d is already deleted";
    private static final String PROCEDURE_WITH_ID_NOT_FOUND = "Procedure with id %d not found.";

    private ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureServiceImpl(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public List<Procedure> getAllProcedures() {
        return procedureRepository.findAllByProcedureDeletedIsFalseOrderByProcedureNameAsc();
    }


    public Procedure getProcedureByID(Long procedureID) {

        Procedure procedureToFind = procedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(procedureID);

        if (procedureToFind == null) {
            throw new DatabaseItemNotFoundException(String.format(PROCEDURE_WITH_ID_NOT_FOUND, procedureID));
        }
        return procedureToFind;

    }


    public Procedure addProcedure(Procedure procedure) {
        Procedure existingProcedure = procedureRepository.findProcedureByProcedureNameAndProcedureDeletedFalse(procedure.getProcedureName());

        if (existingProcedure != null && existingProcedure.isProcedureDeleted()) {
            throw new DatabaseItemAlreadyDeletedException(String.format(PROCEDURE_DELETED_EXCEPTION_MSG, procedure.getProcedureName()));
        } else if (existingProcedure != null && !existingProcedure.isProcedureDeleted()) {
            throw new DatabaseItemAlreadyExistsException(String.format(PROCEDURE_EXIST_EXCEPTION_MSG, procedure.getProcedureName()));
        }
        return procedureRepository.save(procedure);
    }


    public void deleteProcedure(Long procedureID) {
        Procedure procedureToDelete = procedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(procedureID);

        if (procedureToDelete == null) {
            throw new DatabaseItemNotFoundException(String.format(PROCEDURE_NOT_FOUND_MSG, procedureID));
        }

        if (procedureToDelete.isProcedureDeleted()) {
            throw new DatabaseItemAlreadyDeletedException(String.format(PROCEDURE_WITH_ID_IS_ALREADY_DELETED, procedureID));
        }

        procedureToDelete.setProcedureDeleted();
        procedureRepository.save(procedureToDelete);


    }

    public void editProcedure(Procedure procedure, Long procedureID) {
        Procedure procedureToUpdate = procedureRepository.findProcedureByProcedureIDAndProcedureDeletedFalse(procedureID);

        if (procedureToUpdate == null) {
            throw new DatabaseItemNotFoundException(String.format(PROCEDURE_WITH_ID_NOT_FOUND, procedureID));
        }

        procedureToUpdate.setProcedureName(procedure.getProcedureName());
        procedureToUpdate.setProcedurePrice(procedure.getProcedurePrice());

        procedureRepository.save(procedureToUpdate);
    }


}
