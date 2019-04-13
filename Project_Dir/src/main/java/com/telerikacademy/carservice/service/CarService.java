package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Models;

import java.util.List;

public interface CarService {

    Models getById(Long id);
    List<Models> getAllModels();
}
