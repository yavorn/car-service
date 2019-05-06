package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.CustomerCars;

import java.util.List;

public interface CustomerCarsService {

    List<CustomerCars> getAllCustomerCars();

    List<CustomerCars> getAllCustomerCarsByCustomerId(long id);

    List<CustomerCars> getAllCustomerCarsByCustomerEmail(String email);

    CustomerCars getCustomerCarById(long id);

    void deleteCustomerCar(long id);
}
