package com.telerikacademy.carservice.repository;


import com.telerikacademy.carservice.models.CarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarEventRepository extends JpaRepository<CarEvent, Integer> {

    CarEvent findCarEventByCarEventID(Long id);

    List<CarEvent> findCarEventByCustomerCar_CustomerCarID(Long id);

    List<CarEvent> findAllByCustomerCar_CustomerCarID(Long id);

    void deleteById(Long carEventID);
}
