package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.CustomerDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer findByEmail(String email);

    void addCustomer(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException;

    void addAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException;

    void resetPassword(String username);

    List<Integer> listOfYears();

    CustomerCars getCustomerCarById(long id);

    List<CustomerCars> getAllCustomerCars();

    void changePassword(CustomerDto customerDto);

    void disableCustomer(CustomerDto customerDto);

    void enableCustomer(CustomerDto customerDto);
}
