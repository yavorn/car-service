package com.telerikacademy.carservice.repository;


import com.telerikacademy.carservice.models.ProcedureVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureVisitRepository extends JpaRepository<ProcedureVisit, Long> {
    List<ProcedureVisit> findAllByCarEvent_CustomerCar_CustomerCarID(Long id);


}
