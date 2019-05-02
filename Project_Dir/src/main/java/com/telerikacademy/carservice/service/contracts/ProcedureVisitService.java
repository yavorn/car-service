package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.ProcedureVisit;

import java.util.List;

public interface ProcedureVisitService {

    List<ProcedureVisit> getAllProcedureVisits();
   List<ProcedureVisit> getAllProcedureVisitsByCarEventCustomerCarID(Long id);
   void addProcedureVisit(ProcedureVisit procedureVisit);
}
