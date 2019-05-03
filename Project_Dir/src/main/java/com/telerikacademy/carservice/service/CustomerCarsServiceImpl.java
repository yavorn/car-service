package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
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
    public CustomerCarsServiceImpl(CustomerCarsRepository customerCarsRepository) {
        this.customerCarsRepository = customerCarsRepository;
    }

    @Override
    public List<CustomerCars> getAllCustomerCars() {
        List<CustomerCars> result = customerCarsRepository.findAll();
        if (result.size() == 0) {
            throw new DatabaseItemNotFoundException("No customer cars found.");
        }
        return result;
    }

    @Override
    public List<CustomerCars> getAllCustomerCarsByCustomerId(Long id) {
        List<CustomerCars> result = customerCarsRepository.findCustomerCarsByCustomer_CustomerId(id);
        if (result.size() == 0) {
            throw new DatabaseItemNotFoundException(String.format("No customer cars found for customer with id %d", id));
        }
        return result;
    }
}
