package com.telerikacademy.carservice.controllers;


import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureRestController {
    private ProcedureService ProcedureService;


    @Autowired
    public ProcedureRestController(ProcedureService ProcedureService) {
        this.ProcedureService = ProcedureService;
    }

    @GetMapping
    public List<Procedure> allProcedures() {
        return ProcedureService.getAllProcedures();
    }

    @GetMapping("/{procedureID}")
    public Procedure getProcedureById(@PathVariable int procedureID) {
        return ProcedureService.getProcedureByID(procedureID);
    }


    @PostMapping
    public void createProcedure(@RequestBody Procedure procedure) {
        ProcedureService.addProcedure(procedure);
    }

    @DeleteMapping("/delete/{procedureID}")
    public void deleteProcedure(@PathVariable int procedureID) {
        ProcedureService.deleteProcedure(procedureID);
    }

}
