package com.telerikacademy.carservice.controllers;


import com.telerikacademy.carservice.models.Service;
import com.telerikacademy.carservice.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceRestController {
    private ServiceService serviceService;


    @Autowired
    public ServiceRestController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<Service> allServices() {
        return serviceService.getAllServices();
    }


    @PostMapping()
    public void createService(@RequestBody Service service) {
        serviceService.addService(service);
    }

}
