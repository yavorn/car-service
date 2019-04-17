package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.repository.CustomerRepository;
import com.telerikacademy.carservice.service.contracts.EmailService;
import com.telerikacademy.carservice.service.contracts.PassayService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private PassayService passwordService;
    private EmailService emailService;
    private SimpleMailMessage emailTemplate;

    @Autowired
    public CustomerServiceImpl (CustomerRepository customerRepository, UserDetailsManager userDetailsManager,
                                PasswordEncoder passwordEncoder, PassayService passwordService, EmailService emailService, SimpleMailMessage emailTemplate) {
        this.customerRepository = customerRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException {
        createCustomerOrAdmin(customerDto, authorities);
    }

    @Override
    public void addAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException {
        createCustomerOrAdmin(customerDto, authorities);
    }


    private void createCustomerOrAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) throws UsernameExistsException {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (existingCustomer != null) {
            throw new UsernameExistsException(String.format("User with username %s already exists", customerDto.getEmail()));
        }

        String password = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(password);

        Customer newCustomer = new Customer();
        newCustomer.setEmail(customerDto.getEmail());
        newCustomer.setCustomerPassword(password);
        newCustomer.setPhone(customerDto.getPhone());
        newCustomer.setName(customerDto.getName());

        User newUser = new User(customerDto.getEmail(),passwordEncoded,authorities);

        try {
            userDetailsManager.createUser(newUser);
            customerRepository.saveAndFlush(newCustomer);
            emailService.sendSimpleMessageUsingTemplateWhenCreatingCustomer(newCustomer.getEmail(),
                    emailTemplate, newCustomer.getName(), newCustomer.getEmail(), newCustomer.getCustomerPassword());
        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        } catch (DatabaseItemNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }
}
