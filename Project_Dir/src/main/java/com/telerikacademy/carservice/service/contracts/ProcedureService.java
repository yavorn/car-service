package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.Procedure;

import java.util.List;

public interface ProcedureService {
    List<Procedure> getAllProcedures();

    Procedure getProcedureByID(Long procedureID);

    Boolean checkIfProcedureExists(String name);

    Procedure addProcedure(Procedure procedure);

    void deleteProcedure(Long procedureID);

    void editProcedure(Procedure procedure, Long id);

}
