package com.telerikacademy.carservice.service.contracts;



import com.telerikacademy.carservice.models.CarEvent;

import java.util.List;

public interface CarEventService {
    List<CarEvent> getAllCarEvents();

    CarEvent getCarEventByID(long CarEventID);

    List<CarEvent> getCarEventsByCustomerCarID(long id);

    void addCarEvent(CarEvent CarEvent);

    void deleteCarEvent(long CarEventID);

    void editCarEvent(CarEvent carEvent, long carEventID);

    public void editPdfGenerated(CarEvent carEvent);
}
