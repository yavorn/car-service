package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

    Procedure findProcedureByProcedureID(Long id);

    List<Procedure> findAllByProcedureDeletedIsFalse();


}
