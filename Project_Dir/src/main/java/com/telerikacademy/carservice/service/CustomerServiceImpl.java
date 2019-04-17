package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public CustomerServiceImpl (CustomerRepository customerRepository, UserDetailsManager userDetailsManager) {
        this.customerRepository = customerRepository;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer registerNewUserAccount(@ModelAttribute Customer customer) throws UsernameExistsException {
       Customer existingCustomer = customerRepository.findCustomerByEmail(customer.getEmail());
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

       if (existingCustomer != null) {
           throw new UsernameExistsException(String.format("User with username %s already exists", customer.getEmail()));
       }

        org.springframework.security.core.userdetails.User newUser = new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getCustomerPassword(),
                authorities
        );
        userDetailsManager.createUser(newUser);
        return customerRepository.saveAndFlush(customer);
    }
}
