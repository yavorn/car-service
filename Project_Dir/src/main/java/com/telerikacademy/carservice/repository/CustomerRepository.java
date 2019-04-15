package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
