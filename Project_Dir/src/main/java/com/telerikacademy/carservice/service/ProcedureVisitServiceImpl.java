package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.ProcedureVisit;
import com.telerikacademy.carservice.repository.ProcedureVisitRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureVisitServiceImpl implements ProcedureVisitService {

    private ProcedureVisitRepository procedureVisitRepository;

    @Autowired
    public ProcedureVisitServiceImpl (ProcedureVisitRepository procedureVisitRepository) {
        this.procedureVisitRepository = procedureVisitRepository;
    }
    @Override
    public List<ProcedureVisit> getAllProcedureVisits() {
        return procedureVisitRepository.findAll();
    }

    @Override
    public List<ProcedureVisit> getAllProcedureVisitsByCarEventCustomerCarID(long id) {

        List<ProcedureVisit> visits = procedureVisitRepository.findAllByCarEvent_CustomerCar_CustomerCarID(id);

        if (visits.size() == 0) {
            throw new DatabaseItemNotFoundException(String.format("Procedure visit with id %d not found.", id));
        }
        return visits;
    }

    @Override
    public void editPdfGenerated(ProcedureVisit procedureVisit) {

        if (procedureVisit == null) {
            throw new DatabaseItemNotFoundException(String.format("Procedure visit with id %d found!", procedureVisit.getProcedureVisitID()));
        } else {
            procedureVisit.setPdfGenerated(true);
            procedureVisitRepository.save(procedureVisit);
        }
    }

    public ProcedureVisit findProcedureVisitByCarEventId(long id) {
        ProcedureVisit procedureVisitToFind = procedureVisitRepository.findProcedureVisitByCarEvent_CarEventID(id);

        if (procedureVisitToFind == null) {
            throw new DatabaseItemNotFoundException(String.format("Procedure visit for event with id %id not found.", id));
        }
        return procedureVisitToFind;
    }
}
