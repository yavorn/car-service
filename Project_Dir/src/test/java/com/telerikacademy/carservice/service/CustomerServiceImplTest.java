package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.models.*;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.repository.CustomerRepository;
import com.telerikacademy.carservice.service.contracts.EmailService;
import com.telerikacademy.carservice.service.contracts.PassayService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerCarsRepository customerCarsRepository;
    @Mock
    UserDetailsManager userDetailsManager;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    PassayService passwordService;
    @Mock
    EmailService emailService;
    @Mock
    SimpleMailMessage emailTemplate;
    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(new Customer("email", "phone", "name", 0)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertEquals(Arrays.<Customer>asList(new Customer("email", "phone", "name", 0)), result);
    }

    @Test
    public void testFindByEmail() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", "phone", "name", 0));

        Customer result = customerServiceImpl.findByEmail("email");
        Assert.assertEquals(new Customer("email", "phone", "name", 0), result);
    }

    @Test
    public void testAddCustomer() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", "phone", "name", 0));
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.addCustomer(new CustomerDto(), Arrays.<GrantedAuthority>asList(null));
    }

    @Test
    public void testAddAdmin() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", "phone", "name", 0));
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.addAdmin(new CustomerDto(), Arrays.<GrantedAuthority>asList(null));
    }

    @Test
    public void testResetPassword() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", null, null, 0));
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test
    public void testChangePassword() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", "phone", "name", 0));

        customerServiceImpl.changePassword(new CustomerDto());
    }

    @Test
    public void testGetCustomerCarById() throws Exception {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(new CustomerCars(new Customer("email", "phone", "name", 0), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber"));

        CustomerCars result = customerServiceImpl.getCustomerCarById(0L);
        Assert.assertEquals(new CustomerCars(new Customer("email", "phone", "name", 0), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber"), result);
    }

    @Test
    public void testGetAllCustomerCars() throws Exception {
        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();
        Assert.assertEquals(Arrays.<CustomerCars>asList(new CustomerCars(new Customer("email", "phone", "name", 0), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber")), result);
    }

    

    @Test
    public void testEnableCustomer() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", null, null, 0));

        customerServiceImpl.enableCustomer(new CustomerDto());
    }

    @Test
    public void testDisableCustomer() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(new Customer("email", null, null, 0));

        customerServiceImpl.disableCustomer(new CustomerDto());
    }

    @Test
    public void testListOfYears() throws Exception {
        List<Integer> result = customerServiceImpl.listOfYears();
        Assert.assertEquals(Arrays.<Integer>asList(Integer.valueOf(0)), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme