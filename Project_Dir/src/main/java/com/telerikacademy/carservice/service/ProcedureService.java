package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Procedure;

import java.util.List;

public interface ProcedureService {
    List<Procedure> getAllProcedures();

    Procedure getProcedureByID(int procedureID);

    void addProcedure(Procedure procedure);

    void deleteProcedure(int procedureID);

}
