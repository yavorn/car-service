package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.repository.CarEventRepository;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarEventServiceImpl implements CarEventService {
    @Autowired
    CarEventRepository carEventRepository;

    public List<CarEvent> getAllCarEvents() {
        return carEventRepository.findAll();
    }

    @Override
    public CarEvent getCarEventByID(int carEventID) {
        return carEventRepository.findCarEventByCarEventID(carEventID);
    }

    @Override
    public void addCarEvent(CarEvent carEvent) {
        carEventRepository.save(carEvent);
    }

    @Override
    public void deleteCarEvent(int carEventID) {
        carEventRepository.deleteById(carEventID);
    }


}
