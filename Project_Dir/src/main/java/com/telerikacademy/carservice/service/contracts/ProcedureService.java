package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.Procedure;

import java.util.List;

public interface ProcedureService {
    List<Procedure> getAllProcedures();

    Procedure getProcedureByID(Long procedureID);

    void addProcedure(Procedure procedure);

    void deleteProcedure(Long procedureID);

}