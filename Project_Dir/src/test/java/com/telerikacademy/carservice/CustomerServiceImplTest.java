package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.exceptions.UserRightsNotDisabledException;
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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
        customer.setCustomerId(1L);
        customer.setEmail("email");
        customer.setPhone("phone");
        customer.setName("name");
        customer.setIsDeleted(0);

        customerDto.setEmail("email");
        customerDto.setPassword("password");
        customerDto.setPasswordConfirmation("passwordConfirm");
        customerDto.setPhone("phone");
        customerDto.setName("name");
        customerDto.setIsDeleted(0);
    }

    @Test
    public void testGetAllCustomers_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", 0),
                new Customer("email1", "phone", "name1", 0)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testGetAllCustomers_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", 0),
                new Customer("email1", "phone", "name1", 0)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertNotEquals(3, result.size());
    }

    @Test
    public void testFindByEmail_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString()))
                .thenReturn(customer);

        Customer result = customerServiceImpl.findByEmail("email");
        Assert.assertEquals("email", result.getEmail());
    }

    @Test
    public void testFindByEmail_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmail("email"))
                .thenReturn(customer);

        Customer customerToFind = customerServiceImpl.findByEmail("aaa@aa.aa");
        Assert.assertNull(customerToFind);
    }

    @Test(expected = UsernameExistsException.class)
    public void addCustomer_ShouldThrow_WhenUserExists() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
    }

    @Test
    public void testAddCustomer_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
        Customer testCustomer = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName(), customerDto.getIsDeleted());
        List<Customer> result = new ArrayList<>();
        result.add(testCustomer);

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testAddAdmin_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
        Customer testAdmin = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName(), customerDto.getIsDeleted());
        List<Customer> result = new ArrayList<>();
        result.add(testAdmin);

        Assert.assertEquals(1, result.size());
    }

    @Test(expected = UsernameExistsException.class)
    public void addAdmin_ShouldThrow_WhenUserExists() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
    }

    @Test
    public void testResetPassword_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void testResetPassword_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = NullPointerException.class)
    public void testChangePassword_ShouldThrow_WhenNullPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customerServiceImpl.changePassword(new CustomerDto());
    }

//    TODO: finish test
//    @Test
//    public void testChangePassword_ShouldReturn_WhenValidArgsPassed() {
//        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
//        when(passwordEncoder.encode("passwordConfirm"))
//                .thenReturn("$2y$10$ejMX5LHwxCBLilp9udPX5uwIF1oSZs9Nr60p2GejfmHFMsBm1hM.e");
//
//    }

    @Test
    public void testListOfYears_ShouldReturn_WhenValidArgsPassed() {
        int startYear = 1960;
        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> listYears = new ArrayList<>();
        int sizeToCheck = 0;
        for (int index = endYear; index >= startYear; index--) {
            sizeToCheck++;
            listYears.add(sizeToCheck);
        }

        List<Integer> result = customerServiceImpl.listOfYears();

        Assert.assertEquals(sizeToCheck, result.size());
    }

    @Test
    public void testGetAllCustomerCars_ShouldReturn_WhenValidArgsPassed() {
        when(customerServiceImpl.getAllCustomerCars()).thenReturn(Arrays.<CustomerCars>asList(new CustomerCars(customer,
                new Models(new Make("makeName"), "modelName"),
                Integer.valueOf(0), "licensePlate", "VINnumber")));

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        Assert.assertEquals(1, result.size());
    }

    @Test(expected = NullPointerException.class)
    public void testGetAllCustomerCars_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerServiceImpl.getAllCustomerCars()).thenReturn(null);

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testGetCustomerCarById_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber"));

        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
        List<CustomerCars> result = new ArrayList<>();
        result.add(car);
        Assert.assertEquals(1, result.size());
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetCustomerCarById_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(null);
        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
    }

    @Test
    public void testDisableCustomer_ShouldReturn_WhenValidArgsPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customerServiceImpl.disableCustomer(customerDto);

        Assert.assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void testDisableCustomer_ShouldThrow_WhenNullPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        customerServiceImpl.disableCustomer(customerDto);

        Assert.assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemAlreadyDeletedException.class)
    public void testDisableCustomer_ShouldThrow_WhenUserAlreadyDisabled(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(1);
        customerServiceImpl.disableCustomer(customerDto);
    }

    @Test
    public void testEnableCustomer_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(1);
        customerServiceImpl.enableCustomer(customerDto);

        Assert.assertEquals(0, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void testEnableCustomer_ShouldThrow_WhenNullPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        customerServiceImpl.enableCustomer(customerDto);

        Assert.assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = UserRightsNotDisabledException.class)
    public void testEnableCustomer_ShouldThrow_WhenUserAlreadyEnabled(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(0);
        customerServiceImpl.enableCustomer(customerDto);
    }
}

