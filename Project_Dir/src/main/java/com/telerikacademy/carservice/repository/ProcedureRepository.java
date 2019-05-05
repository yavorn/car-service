package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Procedure findProcedureByProcedureIDAndProcedureDeletedFalse(Long id);

    Procedure findProcedureByProcedureNameAndProcedureDeletedFalse(String name);

    List<Procedure> findAllByProcedureDeletedIsFalseOrderByProcedureNameAsc();

    List<Procedure> findAll();
}
