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

    private static final String PROCEDURE_LIST_EMPTY = "Procedure list is empty!";
    private static final String CAR_EVENT_BY_CAR_ID_EXCEPTION = "Car events for car ID %d not found!";
    private static final String CAR_EVENT_BY_ID_EXCEPTION = "Car event with ID %d not found!";
    private static final String CAR_EVENT_EXCEPTION = "No car events found!";


    private CarEventRepository carEventRepository;

    @Autowired
    public CarEventServiceImpl(CarEventRepository carEventRepository) {
        this.carEventRepository = carEventRepository;
    }

    public List<CarEvent> getAllCarEvents() {
        List<CarEvent> result = carEventRepository.findAll();
        if (result.size() == 0) throw new DatabaseItemNotFoundException(CAR_EVENT_EXCEPTION);
        return result;
    }

    @Override
    public CarEvent getCarEventByID(long id) {
        CarEvent eventToFind = carEventRepository.findCarEventByCarEventID(id);
        if (eventToFind == null) throw new DatabaseItemNotFoundException(String.format(CAR_EVENT_BY_ID_EXCEPTION, id));
        return eventToFind;
    }

    @Override
    public List<CarEvent> getCarEventsByCustomerCarID(long id) {
        List<CarEvent> result = carEventRepository.findAllByCustomerCar_CustomerCarID(id);
        if (result.size() == 0) throw new DatabaseItemNotFoundException(String.format(CAR_EVENT_BY_CAR_ID_EXCEPTION, id));

        return result;
    }

    @Override
    public void addCarEvent(CarEvent carEvent) {
        Set<Procedure> procedures = carEvent.getProcedures();
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException(PROCEDURE_LIST_EMPTY);

        double totalPrice = calculateCarEventTotalPrice(procedures);
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
            throw new DatabaseItemNotFoundException(String.format(CAR_EVENT_BY_ID_EXCEPTION, id));
        }


        Set<Procedure> procedures = carEvent.getProcedures();
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException(PROCEDURE_LIST_EMPTY);
        double totalPrice = calculateCarEventTotalPrice(procedures);
        eventToChange.setProcedures(carEvent.getProcedures());
        eventToChange.setFinalized(carEvent.getFinalized());
        eventToChange.setTotalPrice(totalPrice);

        carEventRepository.save(eventToChange);
    }

    @Override
    public void editPdfGenerated(CarEvent carEvent) {
        carEvent.setPdfGenerated(true);
        carEventRepository.save(carEvent);
    }

    private double calculateCarEventTotalPrice(Set<Procedure> procedures) {
        double price = 0.0;
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException(PROCEDURE_LIST_EMPTY);

        for (Procedure pr : procedures) {
            price += pr.getProcedurePrice();
        }
        return price;
    }
}
