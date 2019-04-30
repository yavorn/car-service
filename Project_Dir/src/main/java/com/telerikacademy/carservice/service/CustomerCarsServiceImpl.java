package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.service.contracts.CustomerCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCarsServiceImpl implements CustomerCarsService {

    private CustomerCarsRepository customerCarsRepository;

    @Autowired
    public CustomerCarsServiceImpl(CustomerCarsRepository customerCarsRepository){
        this.customerCarsRepository = customerCarsRepository;
    }

    @Override
    public List<CustomerCars> getAllCustomerCars() {
        return customerCarsRepository.findAll();
    }

    @Override
    public List<CustomerCars> getAllCustomerCarsByCustomerId(Long id) {
        return customerCarsRepository.findCustomerCarsByCustomerID(id);
    }
}
