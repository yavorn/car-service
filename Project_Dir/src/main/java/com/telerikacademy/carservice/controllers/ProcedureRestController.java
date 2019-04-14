package com.telerikacademy.carservice.controllers;


import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureRestController {
    private ProcedureServiceImpl procedureServiceImpl;


    @Autowired
    public ProcedureRestController(ProcedureServiceImpl procedureServiceImpl) {
        this.procedureServiceImpl = procedureServiceImpl;
    }

    @GetMapping
    public List<Procedure> allProcedures() {
        return procedureServiceImpl.getAllProcedures();
    }


    @PostMapping
    public void createProcedure(@RequestBody Procedure procedure) {
        procedureServiceImpl.addProcedure(procedure);
    }

    @DeleteMapping("/delete/{procedureID}")
    public void deleteProcedure(@PathVariable int procedureID) {
        procedureServiceImpl.deleteProcedure(procedureID);
    }

}
