package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository <Service, Integer> {

}
