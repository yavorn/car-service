package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    ProcedureRepository procedureRepository;

    public List<Procedure> getAllProcedures() {
        try {
            return procedureRepository.findAll();
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
            procedureRepository.deleteById(procedureID);
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }


}
