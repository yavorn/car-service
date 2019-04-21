package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.CustomerCars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCarsRepository extends JpaRepository<CustomerCars, Long> {

    CustomerCars findCustomerCarsByCustomerCarID(Long id);
}
