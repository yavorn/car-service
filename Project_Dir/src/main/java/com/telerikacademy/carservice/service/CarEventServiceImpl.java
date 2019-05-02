package com.telerikacademy.carservice.service;


import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.repository.CarEventRepository;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarEventServiceImpl implements CarEventService {

    private CarEventRepository carEventRepository;

    @Autowired
    public CarEventServiceImpl(CarEventRepository carEventRepository) {
        this.carEventRepository = carEventRepository;
    }

    public List<CarEvent> getAllCarEvents() {
        return carEventRepository.findAll();
    }

    @Override
    public CarEvent getCarEventByID(Long carEventID) {
        return carEventRepository.findCarEventByCarEventID(carEventID);
    }

    @Override
    public List<CarEvent> getCarEventByCustomerCarID(Long id) {


          try {
            List<CarEvent> existingCarEvents = getAllCarEvents()
                    .stream()
                    .filter(event -> event.getCustomerCar().getCustomerCarID() .equals(id))
                    .collect(Collectors.toList());

            if (existingCarEvents.size() == 0) {
                throw new DatabaseItemNotFoundException(String.format("Car event with id %d not found.", id));
            }
            return carEventRepository.findAllByCustomerCar_CustomerCarID(id);

        }  catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );

        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }

    }

    @Override
    public Long getCarEventIDbyCustomerCarID(Long id) {
        return null;
    }


    @Override
    public void addCarEvent(CarEvent carEvent) {
        carEventRepository.save(carEvent);
    }

    @Override
    public void deleteCarEvent(Long carEventID) {
        carEventRepository.deleteById(carEventID);
    }

    @Override
    public void editCarEvent(CarEvent carEvent, Long carEventID) {
        CarEvent eventToChange = carEventRepository.findCarEventByCarEventID(carEventID);

        if (eventToChange == null) {
            throw new DatabaseItemNotFoundException(String.format("Car event with id %d not found.", carEventID));
        }

        eventToChange.setCustomerCar(carEvent.getCustomerCar());
        eventToChange.setDate(carEvent.getDate());
        eventToChange.setFinalized(carEvent.getFinalized());
        eventToChange.setTotalPrice(carEvent.getTotalPrice());

        carEventRepository.saveAndFlush(eventToChange);
    }
}
