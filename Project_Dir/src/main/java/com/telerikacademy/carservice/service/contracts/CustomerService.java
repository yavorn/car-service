package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.CustomerDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer findByEmail(String email);

    void addCustomer(CustomerDto customerDto, List<GrantedAuthority> authorities);

    void addAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities);

    void resetPassword(String username);

    List<Integer> listOfYears();

    CustomerCars getCustomerCarById(long id);

    List<CustomerCars> getAllCustomerCars();

    void changePassword(CustomerDto customerDto);

    void disableCustomer(CustomerDto customerDto);

    void enableCustomer(CustomerDto customerDto);

    void createCustomerCar(CustomerCars carToAdd, String email);
}
