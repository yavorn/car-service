package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    void addCustomer(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException;

    void addAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException;
}
