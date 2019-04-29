package com.telerikacademy.carservice.controllers.REST;

import com.telerikacademy.carservice.models.ProcedureVisit;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/procedurevisits")
public class ProcedureVisitRestController {
    private ProcedureVisitService procedureVisitService;

    @Autowired
    public ProcedureVisitRestController(ProcedureVisitService procedureVisitService) {
        this.procedureVisitService = procedureVisitService;
    }

    @GetMapping
    public List<ProcedureVisit> getAllProcedures() {
        return procedureVisitService.getAllProcedureVisits();
    }
}
