package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByIsDeletedFalseOrderByNameAsc();

    Customer findCustomerByEmailAndIsDeletedFalse(String email);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password where username = :username", nativeQuery = true)
    void updatePassword(@Param(value = "password") String password, @Param(value = "username") String username);

    @Transactional
    @Modifying
    @Query(value = "update users set enabled = '0' where username = :username", nativeQuery = true)
    void disableUser(@Param(value = "username") String username);

    @Transactional
    @Modifying
    @Query(value = "update users set enabled = '1' where username = :username", nativeQuery = true)
    void enableUser(@Param(value = "username") String username);
}
