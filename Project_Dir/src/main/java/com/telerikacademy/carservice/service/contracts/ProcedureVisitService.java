package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.ProcedureVisit;

import java.util.List;

public interface ProcedureVisitService {

    List<ProcedureVisit> getAllProcedureVisits();

    List<ProcedureVisit> getAllProcedureVisitsByCarEventCustomerCarID(long id);

    void editPdfGenerated(ProcedureVisit procedureVisit);

    ProcedureVisit findProcedureVisitByCarEventId(long id);
}
