package com.telerikacademy.carservice.service.contracts;



import com.telerikacademy.carservice.models.CarEvent;

import java.util.List;

public interface CarEventService {
    List<CarEvent> getAllCarEvents();

    CarEvent getCarEventByID(int CarEventID);
    List<CarEvent> getCarEventByCustomerCarID(Long id);

    Long getCarEventIDbyCustomerCarID(Long id);

    void addCarEvent(CarEvent CarEvent);

    void deleteCarEvent(int CarEventID);
    
}
