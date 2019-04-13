package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Models, Integer> {

    Models findModelsByModelID(Long id);


}
