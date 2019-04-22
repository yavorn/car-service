package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAll();

    Customer findCustomerByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password where username = :username", nativeQuery = true)
    void userToUpdatePassword(@Param(value = "password") String password, @Param(value = "username") String username);
}
