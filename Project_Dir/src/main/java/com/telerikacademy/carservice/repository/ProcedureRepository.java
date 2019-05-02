package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Procedure findProcedureByProcedureID(Long id);

    Procedure findProcedureByProcedureName(String name);

    Boolean existsByProcedureName(String name);

    List<Procedure> findAllByProcedureDeletedIsFalse();

    List<Procedure> findAll();
}
