package com.telerikacademy.carservice.models.contracts;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer registerNewUserAccount(@ModelAttribute Customer customer) throws UsernameExistsException;
}
