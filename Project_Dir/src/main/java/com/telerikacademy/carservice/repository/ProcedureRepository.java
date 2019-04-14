package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository <Procedure, Integer> {

}
