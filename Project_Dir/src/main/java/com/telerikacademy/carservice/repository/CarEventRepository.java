package com.telerikacademy.carservice.repository;


import com.telerikacademy.carservice.models.CarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarEventRepository extends JpaRepository<CarEvent, Long> {

    CarEvent findCarEventByCarEventID(long id);

    CarEvent findCarEventByCarEventIDAndCarEventDeletedFalse(long id);

    List<CarEvent> findAllByCustomerCar_CustomerCarID(long id);

    void deleteById(long carEventID);
}
