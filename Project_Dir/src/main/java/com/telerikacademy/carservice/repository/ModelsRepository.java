package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModelsRepository extends JpaRepository<Models, Long> {

    Models findModelsByModelID(Long id);

    List<Models> findModelsByMake_MakeID(Long id);


}
