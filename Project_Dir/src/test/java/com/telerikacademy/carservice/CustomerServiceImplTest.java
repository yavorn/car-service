package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.UsernameExistsException;
import com.telerikacademy.carservice.models.*;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.repository.CustomerRepository;
import com.telerikacademy.carservice.service.CustomerServiceImpl;
import com.telerikacademy.carservice.service.contracts.EmailService;
import com.telerikacademy.carservice.service.contracts.PassayService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
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
    private Customer customer = new Customer();
    private CustomerDto customerDto = new CustomerDto();
    private List<GrantedAuthority> userAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
    private List<GrantedAuthority> adminAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customer.setEmail("email");
        customer.setPhone("phone");
        customer.setName("name");

        customerDto.setEmail("someEmail");
        customerDto.setPassword("password");
        customerDto.setPasswordConfirmation("passwordConfirm");
        customerDto.setPhone("phone");
        customerDto.setName("name");
    }

//    OK
    @Test
    public void testGetAllCustomers_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name"),
                new Customer("email1", "phone", "name1")));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertEquals(2, result.size());
    }

//    OK
    @Test
    public void testGetAllCustomers_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name"),
                new Customer("email1", "phone", "name1")));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertNotEquals(3, result.size());
    }

//    OK
    @Test
    public void testFindByEmail_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString()))
                .thenReturn(customer);

        Customer result = customerServiceImpl.findByEmail("email");
        Assert.assertEquals("email", result.getEmail());
    }

//    OK
    @Test
    public void testFindByEmail_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmail("email"))
        .thenReturn(customer);

        Customer customerToFind = customerServiceImpl.findByEmail("aaa@aa.aa");
        Assert.assertNull(customerToFind);
    }



//    OK
    @Test(expected = UsernameExistsException.class)
    public void addCustomer_ShouldThrow_WhenUserExists() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
    }

//    OK
    @Test
    public void testAddCustomer_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
        Customer testCustomer = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName());
        List<Customer> result = new ArrayList<>();
        result.add(testCustomer);

        Assert.assertEquals(1, result.size());
    }
//OK
    @Test
    public void testAddAdmin_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
        Customer testAdmin = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName());
        List<Customer> result = new ArrayList<>();
        result.add(testAdmin);

        Assert.assertEquals(1, result.size());
    }
//OK
    @Test
    public void testResetPassword() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = NullPointerException.class)
    public void testChangePassword_ShouldReturn_WhenNullPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);

        customerServiceImpl.changePassword(new CustomerDto());
    }

    @Test
    public void testChangePassword_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);

        customerServiceImpl.changePassword(new CustomerDto());
    }

    @Test
    public void testListOfYears() {
        List<Integer> result = customerServiceImpl.listOfYears();
        Assert.assertEquals(Arrays.<Integer>asList(Integer.valueOf(0)), result);
    }

    @Test
    public void testGetAllCustomerCars() {
        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();
        Assert.assertEquals(Arrays.<CustomerCars>asList(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber")), result);
    }

    @Test
    public void testGetCustomerCarById() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber"));

        CustomerCars result = customerServiceImpl.getCustomerCarById(0L);
        Assert.assertEquals(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber"), result);
    }
}
