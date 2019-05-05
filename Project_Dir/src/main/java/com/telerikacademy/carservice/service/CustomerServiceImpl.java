package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.*;
import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.repository.CustomerRepository;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import com.telerikacademy.carservice.service.contracts.EmailService;
import com.telerikacademy.carservice.service.contracts.PassayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_NOT_FOUND_ERROR_MESSAGE = "Customer with email %s not found.";
    private static final String USER_ALREADY_EXISTS_ERROR_MESSAGE = "User with username %s already exists";
    private static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car with id %d not found";
    private static final String NO_CARS_FOUND_ERROR_MESSAGE = "No cars found";
    private static final String CUSTOMER_ALREADY_HAS_CAR_ERROR_MESSAGE = "Car with id %d is already added to customer %s";
    private CustomerRepository customerRepository;
    private CustomerCarsRepository customerCarsRepository;
    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;
    private PassayService passwordService;
    private EmailService emailService;
    private SimpleMailMessage emailTemplate;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerCarsRepository customerCarsRepository,
                               UserDetailsManager userDetailsManager,
                               PasswordEncoder passwordEncoder,
                               PassayService passwordService,
                               EmailService emailService,
                               SimpleMailMessage emailTemplate) {
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
        return customerRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findCustomerByEmail(String.format(CUSTOMER_NOT_FOUND_ERROR_MESSAGE, email));
    }

    @Override
    public void addCustomer(CustomerDto customerDto, List<GrantedAuthority> authorities) {
        createCustomerOrAdmin(customerDto, authorities);
    }

    @Override
    public void addAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) {
        createCustomerOrAdmin(customerDto, authorities);
    }

    @Override
    public void resetPassword(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);

        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_NOT_FOUND_ERROR_MESSAGE, email));
        }

        String generatedNewPassword = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(generatedNewPassword);

        customerRepository.updatePassword(passwordEncoded, customer.getEmail());
        customerRepository.saveAndFlush(customer);
        emailService.sendSimpleMessageForPasswordResetUsingTemplate(customer.getEmail(),
                "Password reset",
                String.format("Dear Customer,\n\n " +
                                "Please find your temporary password: %s\n\n " +
                                "Best Regards,\n " +
                                "Team 6 Car Service",
                        generatedNewPassword));
    }

    @Override
    @Transactional
    public void changePassword(CustomerDto customerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Customer customer = customerRepository.findCustomerByEmail(currentPrincipalName);

        String newPassword = customerDto.getPasswordConfirmation();

        String newEncodedPassword = passwordEncoder.encode(newPassword);
        customerRepository.updatePassword(newEncodedPassword, currentPrincipalName);
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public CustomerCars getCustomerCarById(long id) {
        CustomerCars carToFind = customerCarsRepository.findCustomerCarsByCustomerCarID(id);

        if (carToFind == null) {
            throw new DatabaseItemNotFoundException(String.format(CAR_NOT_FOUND_ERROR_MESSAGE, id));
        }
        return carToFind;
    }

    @Override
    public List<CustomerCars> getAllCustomerCars() {
        List<CustomerCars> allCars = customerCarsRepository.findAll();

        if (allCars.size() == 0) {
            throw new DatabaseItemNotFoundException(NO_CARS_FOUND_ERROR_MESSAGE);
        }
        return allCars;
    }

    @Override
    @Transactional
    public void enableCustomer(CustomerDto customerDto) {
        Customer customerToEnable = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (customerToEnable == null) {
            throw new DatabaseItemNotFoundException(customerDto.getEmail());
        }

        if (!customerToEnable.getIsDeleted()) {
            throw new DatabaseItemAlreadyUnDeletedException(customerDto.getEmail());
        }

        customerRepository.enableUser(customerToEnable.getEmail());
        customerToEnable.setIsDeleted(false);
        customerRepository.saveAndFlush(customerToEnable);
    }

    @Override
    @Transactional
    public void disableCustomer(CustomerDto customerDto) {
        Customer customerToDisable = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (customerToDisable == null) {
            throw new DatabaseItemNotFoundException(customerDto.getName());
        }

        if (customerToDisable.getIsDeleted()) {
            throw new DatabaseItemAlreadyDeletedException(customerToDisable.getEmail());
        }

        customerRepository.disableUser(customerToDisable.getEmail());
        customerToDisable.setIsDeleted(true);
        customerRepository.saveAndFlush(customerToDisable);
    }

    @Override
    public void createCustomerCar(CustomerCars carToAdd, String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);

        if (customerCarsRepository.findAll().contains(carToAdd)) {
            throw new DatabaseItemAlreadyExistsException(String.format(CUSTOMER_ALREADY_HAS_CAR_ERROR_MESSAGE,
                    carToAdd.getCustomerCarID(), email));
        }
        if (customer == null) {
            throw new DatabaseItemNotFoundException(String.format(CUSTOMER_NOT_FOUND_ERROR_MESSAGE, email));
        }

        carToAdd.setCustomer(customer);
        customerCarsRepository.saveAndFlush(carToAdd);
    }

    @Override
    public List<Integer> listOfYears() {
        int startYear = 1960;
        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> listYears = new ArrayList<>();
        for (int i = endYear; i >= startYear; i--) {
            listYears.add(i);
        }
        return listYears;
    }

    private void createCustomerOrAdmin(CustomerDto customerDto, List<GrantedAuthority> authorities) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerDto.getEmail());

        if (existingCustomer != null) {
            throw new DatabaseItemAlreadyExistsException(String.format(USER_ALREADY_EXISTS_ERROR_MESSAGE, customerDto.getEmail()));
        }

        String password = passwordService.generateRandomPassword();
        String passwordEncoded = passwordEncoder.encode(password);

        Customer newCustomer = new Customer();
        newCustomer.setEmail(customerDto.getEmail());
        newCustomer.setPhone(customerDto.getPhone());
        newCustomer.setName(customerDto.getName());

        User newUser = new User(customerDto.getEmail(), passwordEncoded, authorities);

        userDetailsManager.createUser(newUser);
        customerRepository.saveAndFlush(newCustomer);
        emailService.sendSimpleMessageUsingTemplateWhenCreatingCustomer(newCustomer.getEmail(),
                emailTemplate, newCustomer.getName(), newCustomer.getEmail(), password);

    }
}
