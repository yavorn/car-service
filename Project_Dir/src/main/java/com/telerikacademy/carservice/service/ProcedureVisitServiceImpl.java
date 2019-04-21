package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.models.ProcedureVisit;
import com.telerikacademy.carservice.repository.ProcedureVisitRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcedureVisitServiceImpl implements ProcedureVisitService {
    @Autowired
    ProcedureVisitRepository procedureVisitRepository;

    @Override
    public List<ProcedureVisit> getAllProcedureVisits() {
        return procedureVisitRepository.findAll();
    }
}
