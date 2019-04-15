package com.telerikacademy.carservice.repository;


import com.telerikacademy.carservice.models.CarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarEventRepository extends JpaRepository<CarEvent, Integer> {

    CarEvent findCarEventByCarEventID(Integer id);
}
