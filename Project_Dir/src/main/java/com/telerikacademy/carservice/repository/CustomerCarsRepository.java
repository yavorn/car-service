package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.CustomerCars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCarsRepository extends JpaRepository<CustomerCars, Long> {

    CustomerCars findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(Long id);

    List<CustomerCars> findCustomerCarsByCustomer_CustomerIdAndCustomerCarDeletedFalse(long id);

    List<CustomerCars> findCustomerCarsByCustomerEmailAndCustomerCarDeletedFalse(String email);

    List<CustomerCars> findAllByCustomerCarDeletedFalse();
}
