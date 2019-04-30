package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.ProcedureVisit;
import com.telerikacademy.carservice.repository.ProcedureVisitRepository;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcedureVisitServiceImpl implements ProcedureVisitService {
    @Autowired
    ProcedureVisitRepository procedureVisitRepository;

    @Override
    public List<ProcedureVisit> getAllProcedureVisits() {
        return procedureVisitRepository.findAll();
    }

    @Override
    public List<ProcedureVisit> getAllProcedureVisitsByCarEventCustomerCarID(Long id) {


          try {
            List<ProcedureVisit> existingProcedureVisit = getAllProcedureVisits()
                    .stream()
                    .filter(procedureVisits -> procedureVisits
                            .getCarEvent()
                            .getCustomerCar()
                            .getCustomerCarID()
                            .equals(id))
                    .collect(Collectors.toList());

            if (existingProcedureVisit.size() == 0) {
                throw new DatabaseItemNotFoundException(String.format("Procedure visit with id %d not found.", id));
            }
            return procedureVisitRepository.findAllByCarEvent_CustomerCar_CustomerCarID(id);

        }  catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );

        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }



    }

}
