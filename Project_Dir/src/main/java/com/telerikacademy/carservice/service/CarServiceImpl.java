package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Models getById(Long id) {

        Models model;

        model  = carRepository.findModelsByModelID(id);

        return model ;
    }
}
