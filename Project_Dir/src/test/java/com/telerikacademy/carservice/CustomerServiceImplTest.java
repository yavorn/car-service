package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.*;
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
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.Assert.*;
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
    CustomerCars car = new CustomerCars();
    List<CustomerCars> cars = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
    public void getAllCustomers_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", 0),
                new Customer("email1", "phone", "name1", 0)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        assertEquals(2, result.size());
    }

    @Test
    public void getAllCustomers_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", 0),
                new Customer("email1", "phone", "name1", 0)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertNotEquals(3, result.size());
    }

    @Test
    public void findByEmail_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString()))
                .thenReturn(customer);

        Customer result = customerServiceImpl.findByEmail("email");
        assertEquals("email", result.getEmail());
    }

    @Test
    public void findByEmail_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmail("email"))
                .thenReturn(customer);

        Customer customerToFind = customerServiceImpl.findByEmail("aaa@aa.aa");
        Assert.assertNull(customerToFind);
    }

    @Test(expected = UsernameExistsException.class)
    public void customer_ShouldThrow_WhenUserExists() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
    }

    @Test
    public void addCustomer_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
        Customer testCustomer = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName(), customerDto.getIsDeleted());
        List<Customer> result = new ArrayList<>();
        result.add(testCustomer);

        assertEquals(1, result.size());
    }

    @Test
    public void addAdmin_ShouldReturn_WhenValidArgsPassed() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
        Customer testAdmin = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName(), customerDto.getIsDeleted());
        List<Customer> result = new ArrayList<>();
        result.add(testAdmin);

        assertEquals(1, result.size());
    }

    @Test(expected = UsernameExistsException.class)
    public void addAdmin_ShouldThrow_WhenUserExists() throws Exception {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
    }

    @Test
    public void resetPassword_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void resetPassword_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = NullPointerException.class)
    public void changePassword_ShouldThrow_WhenNullPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customerServiceImpl.changePassword(new CustomerDto());
    }

    @Test
    public void listOfYears_ShouldReturn_WhenValidArgsPassed() {
        int startYear = 1960;
        int endYear = Calendar.getInstance().get(Calendar.YEAR);
        int sizeToCheck = 0;
        for (int index = endYear; index >= startYear; index--) {
            sizeToCheck++;
        }

        List<Integer> result = customerServiceImpl.listOfYears();

        assertEquals(sizeToCheck, result.size());
    }

    @Test
    public void getAllCustomerCars_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findAll()).thenReturn(Arrays.<CustomerCars>asList(new CustomerCars(customer,
                new Models(new Make("makeName"), "modelName"),
                Integer.valueOf(0), "licensePlate", "VINnumber")));

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        assertEquals(1, result.size());
    }

    @Test(expected = ResponseStatusException.class)
    public void getAllCustomerCars_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerServiceImpl.getAllCustomerCars()).thenReturn(null);

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        assertEquals(1, result.size());
    }

    @Test
    public void getCustomerCarById_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber"));

        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
        List<CustomerCars> result = new ArrayList<>();
        result.add(car);
        assertEquals(1, result.size());
    }

    @Test(expected = ResponseStatusException.class)
    public void getCustomerCarById_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarID(anyLong())).thenReturn(null);
        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
    }

    @Test
    public void disableCustomer_ShouldReturn_WhenValidArgsPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customerServiceImpl.disableCustomer(customerDto);

        assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void disableCustomer_ShouldThrow_WhenNullPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        customerServiceImpl.disableCustomer(customerDto);

        assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemAlreadyDeletedException.class)
    public void disableCustomer_ShouldThrow_WhenUserAlreadyDisabled(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(1);
        customerServiceImpl.disableCustomer(customerDto);
    }

    @Test
    public void enableCustomer_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(1);
        customerServiceImpl.enableCustomer(customerDto);

        assertEquals(0, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void enableCustomer_ShouldThrow_WhenNullPassed(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(null);
        customerServiceImpl.enableCustomer(customerDto);

        assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = UserRightsNotDisabledException.class)
    public void enableCustomer_ShouldThrow_WhenUserAlreadyEnabled(){
        when(customerRepository.findCustomerByEmail(anyString())).thenReturn(customer);
        customer.setIsDeleted(0);
        customerServiceImpl.enableCustomer(customerDto);
    }

    @Test(expected = DatabaseItemAlreadyExistsException.class)
    public void createCustomerCar_ShouldThrow_WhenCustomerHasThisCarAlready(){
        cars.add(car);
        when(customerRepository.findCustomerByEmail(customerDto.getEmail())).thenReturn(customer);
        when(customerCarsRepository.findAll()).thenReturn(cars);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void createCustomerCar_ShouldThrow_WhenCustomerDoesNotExist(){
        when(customerRepository.findCustomerByEmail(customerDto.getEmail())).thenReturn(null);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
    }

    @Test
    public void createCustomerCar_ShouldReturn_WhenValidArgsPassed(){
        when(customerRepository.findCustomerByEmail(customerDto.getEmail())).thenReturn(customer);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
        assertNotNull(customerCarsRepository.findAll());
    }
}
