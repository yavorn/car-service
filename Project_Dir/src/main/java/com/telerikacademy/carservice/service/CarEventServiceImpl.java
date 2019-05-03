package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.repository.CarEventRepository;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CarEventServiceImpl implements CarEventService {

    private CarEventRepository carEventRepository;

    @Autowired
    public CarEventServiceImpl(CarEventRepository carEventRepository) {
        this.carEventRepository = carEventRepository;
    }

    public List<CarEvent> getAllCarEvents() {
        List<CarEvent> result = carEventRepository.findAll();
        if (result.size() == 0) throw new DatabaseItemNotFoundException("No car events found.");
        return result;
    }

    @Override
    public CarEvent getCarEventByID(long id) {
        CarEvent eventToFind = carEventRepository.findCarEventByCarEventID(id);
        if (eventToFind == null) throw new DatabaseItemNotFoundException(String.format("Car event with id %d not found.", id));
        return eventToFind;
    }

    @Override
    public List<CarEvent> getCarEventsByCustomerCarID(long id) {
        List<CarEvent> result = carEventRepository.findAllByCustomerCar_CustomerCarID(id);
        if (result.size() == 0) throw new DatabaseItemNotFoundException(String.format("Car event for car id %d not found.", id));

        return result;
    }

    @Override
    public void addCarEvent(CarEvent carEvent) {
        Set<Procedure> procedures = carEvent.getProcedures();
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException(String.format("Procedure list for car event with id %d is empty", carEvent.getCarEventID()));

        double totalPrice = carEventPrice(procedures);
        carEvent.setTotalPrice(totalPrice);
        carEvent.setDate(LocalDateTime.now());

        carEventRepository.saveAndFlush(carEvent);
    }

    @Override
    public void deleteCarEvent(long id) {
        carEventRepository.deleteById(id);
    }

    //TODO: functionality not tested in frontend. Will probably throw DatabaseItemAlreadyExistsException!!!
    @Override
    public void editCarEvent(CarEvent carEvent, long id) {
        CarEvent eventToChange = carEventRepository.findCarEventByCarEventID(id);

        if (eventToChange == null) {
            throw new DatabaseItemNotFoundException(String.format("Car event with id %d not found.", id));
        }
        eventToChange.setCustomerCar(carEvent.getCustomerCar());
        eventToChange.setDate(carEvent.getDate());
        eventToChange.setFinalized(carEvent.getFinalized());
        eventToChange.setTotalPrice(carEvent.getTotalPrice());

        carEventRepository.save(eventToChange);
    }

    private double carEventPrice(Set<Procedure> procedures) {
        double price = 0.0;
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException("Procedure list is empty");

        for (Procedure pr : procedures) {
            price += pr.getProcedurePrice();
        }
        return price;
    }
}
