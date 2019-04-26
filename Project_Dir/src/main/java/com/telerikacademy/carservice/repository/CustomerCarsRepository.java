package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.CustomerCars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCarsRepository extends JpaRepository<CustomerCars, Long> {

    CustomerCars findCustomerCarsByCustomerCarID(Long id);

    List<CustomerCars> findCustomerCarsByCustomerID(Long id);
}
