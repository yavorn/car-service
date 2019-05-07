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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

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
        customer.setIsDeleted(false);

        customerDto.setEmail("email");
        customerDto.setPassword("password");
        customerDto.setPasswordConfirmation("passwordConfirm");
        customerDto.setPhone("phone");
        customerDto.setName("name");
        customerDto.setIsDeleted(false);
    }

    @Test
    public void getAllCustomers_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findAllByIsDeletedFalseOrderByNameAsc()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", false),
                new Customer("email1", "phone", "name1", false)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        assertEquals(2, result.size());
    }

    @Test
    public void getAllCustomers_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findAll()).thenReturn(Arrays.<Customer>asList(
                new Customer("email", "phone", "name", false),
                new Customer("email1", "phone", "name1", false)));

        List<Customer> result = customerServiceImpl.getAllCustomers();
        Assert.assertNotEquals(3, result.size());
    }

    @Test
    public void findByEmail_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString()))
                .thenReturn(customer);

        Customer result = customerServiceImpl.findByEmail("email");
        assertEquals("email", result.getEmail());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void findByEmail_ShouldReturn_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse("email"))
                .thenReturn(customer);

        Customer customerToFind = customerServiceImpl.findByEmail("aaa@aa.aa");
        Assert.assertNull(customerToFind);
    }

    @Test(expected = DatabaseItemAlreadyExistsException.class)
    public void customer_ShouldThrow_WhenUserExists() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, userAuthorities);
    }

    @Test
    public void addCustomer_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
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
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
        Customer testAdmin = new Customer(customerDto.getEmail(), customerDto.getPhone(), customerDto.getName(), customerDto.getIsDeleted());
        List<Customer> result = new ArrayList<>();
        result.add(testAdmin);

        assertEquals(1, result.size());
    }

    @Test(expected = DatabaseItemAlreadyExistsException.class)
    public void addAdmin_ShouldThrow_WhenUserExists() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");
        when(passwordEncoder.encode("generateRandomPasswordResponse")).thenReturn("$2y$10$SWb8bU0QIEb067afKMIj6.nTSNXDUDTKLNye/jXi7WBBpXfv8Izg6");
        customerServiceImpl.addCustomer(customerDto, adminAuthorities);
    }

    @Test
    public void resetPassword_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void resetPassword_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
        when(passwordService.generateRandomPassword()).thenReturn("generateRandomPasswordResponse");

        customerServiceImpl.resetPassword("email");
    }

    @Test(expected = NullPointerException.class)
    public void changePassword_ShouldThrow_WhenNullPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
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
        when(customerCarsRepository.findAllByCustomerCarDeletedFalse()).thenReturn(Arrays.<CustomerCars>asList(new CustomerCars(customer,
                new Models(new Make("makeName"), "modelName"),
                Integer.valueOf(0), "licensePlate", "VINnumber")));

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        assertEquals(1, result.size());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getAllCustomerCars_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerServiceImpl.getAllCustomerCars()).thenReturn(null);

        List<CustomerCars> result = customerServiceImpl.getAllCustomerCars();

        assertEquals(1, result.size());
    }

    @Test
    public void getCustomerCarById_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(anyLong())).thenReturn(new CustomerCars(customer, new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "VINnumber"));

        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
        List<CustomerCars> result = new ArrayList<>();
        result.add(car);
        assertEquals(1, result.size());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getCustomerCarById_ShouldThrow_WhenInvalidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomerCarIDAndCustomerCarDeletedFalse(anyLong())).thenReturn(null);
        CustomerCars car = customerServiceImpl.getCustomerCarById(0L);
    }

    @Test
    public void disableCustomer_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        customerServiceImpl.disableCustomer(customerDto);

        assertTrue(customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void disableCustomer_ShouldThrow_WhenNullPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
        customerServiceImpl.disableCustomer(customerDto);

        assertEquals(1, customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemAlreadyDeletedException.class)
    public void disableCustomer_ShouldThrow_WhenUserAlreadyDisabled() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        customer.setIsDeleted(true);
        customerServiceImpl.disableCustomer(customerDto);
    }

    @Test
    public void enableCustomer_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        customer.setIsDeleted(true);
        customerServiceImpl.enableCustomer(customerDto);

        assertFalse(customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void enableCustomer_ShouldThrow_WhenNullPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(null);
        customerServiceImpl.enableCustomer(customerDto);

        assertTrue(customer.getIsDeleted());
    }

    @Test(expected = DatabaseItemAlreadyUnDeletedException.class)
    public void enableCustomer_ShouldThrow_WhenUserAlreadyEnabled() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(anyString())).thenReturn(customer);
        customer.setIsDeleted(false);
        customerServiceImpl.enableCustomer(customerDto);
    }

    @Test(expected = DatabaseItemAlreadyExistsException.class)
    public void createCustomerCar_ShouldThrow_WhenCustomerHasThisCarAlready() {
        cars.add(car);
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(customerDto.getEmail())).thenReturn(customer);
        when(customerCarsRepository.findAllByCustomerCarDeletedFalse()).thenReturn(cars);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void createCustomerCar_ShouldThrow_WhenCustomerDoesNotExist() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(customerDto.getEmail())).thenReturn(null);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
    }

    @Test
    public void createCustomerCar_ShouldReturn_WhenValidArgsPassed() {
        when(customerRepository.findCustomerByEmailAndIsDeletedFalse(customerDto.getEmail())).thenReturn(customer);
        customerServiceImpl.createCustomerCar(car, customer.getEmail());
        assertNotNull(customerCarsRepository.findAll());
    }
}
