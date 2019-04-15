package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAll();

    Customer findCustomerByEmail(String email);
}
