package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    ProcedureRepository procedureRepository;

    public List<Procedure> getAllProcedures() {
        return procedureRepository.findAll();
    }

    public Procedure getProcedureByID(int procedureID) {

        return procedureRepository.findProcedureByProcedureID(procedureID);
    }

    public void addProcedure(Procedure procedure) {
        procedureRepository.save(procedure);
    }

    public void deleteProcedure(int procedureID){
        procedureRepository.deleteById(procedureID);
    }


}
