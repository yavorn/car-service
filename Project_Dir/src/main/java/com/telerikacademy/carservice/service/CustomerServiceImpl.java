package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.repository.CustomerRepository;
import com.telerikacademy.carservice.service.contracts.CustomerService;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerCarsRepository customerCarsRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private PassayService passwordService;
    private EmailService emailService;
    private SimpleMailMessage emailTemplate;

    @Autowired
    public CustomerServiceImpl (CustomerRepository customerRepository
            , CustomerCarsRepository customerCarsRepository
            , UserDetailsManager userDetailsManager
            , PasswordEncoder passwordEncoder
            , PassayService passwordService
            , EmailService emailService
            , SimpleMailMessage emailTemplate) {
        this.customerRepository = customerRepository;
        this.customerCarsRepository = customerCarsRepository;
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

    @Override
    public void resetPassword(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(email);
        }

        String generatedNewPassword = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(generatedNewPassword);

        try {
            customer.setCustomerPassword(generatedNewPassword);
            customerRepository.updatePassword(passwordEncoded, customer.getEmail());
            customerRepository.saveAndFlush(customer);
            emailService.sendSimpleMessageForPasswordResetUsingTemplate(customer.getEmail(),
                    "Password reset",
                    String.format("Dear Customer,\n\n " +
                                    "Please find your temporary password: %s\n\n " +
                                    "Best Regards,\n " +
                                    "Team 6 Car Service",
                            generatedNewPassword));
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
    @Override
    public List<Integer> listOfYears() {
        int startYear = 1960;
        int endYear =  Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> listYears = new ArrayList<>();
        for (int i = endYear; i >= startYear ; i--) {
            listYears.add(i);
        }
        return listYears;
    }

    @Override
    public List<CustomerCars> getAllCustomerCars() {
        try {
            return customerCarsRepository.findAll();

        } catch (HibernateException he) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to access database."
            );
        }
    }

    @Override
    public CustomerCars getCustomerCarById(Long id) {


        try {
            List<CustomerCars> existingCustomerCar = getAllCustomerCars()
                    .stream()
                    .filter(car -> car.getCustomerCarID().equals(id))
                    .collect(Collectors.toList());

            if (existingCustomerCar.size() == 0) {
                throw new DatabaseItemNotFoundException("Customer Car", id);
            }
            return customerCarsRepository.findCustomerCarsByCustomerCarID(id);

        }  catch (HibernateException he) {
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
