package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.Service;
import com.telerikacademy.carservice.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;


    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public void addService(Service service) {
        serviceRepository.save(service);
    }


}
