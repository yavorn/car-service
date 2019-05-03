package com.telerikacademy.carservice;

import com.telerikacademy.carservice.models.Customer;
import com.telerikacademy.carservice.models.CustomerCars;
import com.telerikacademy.carservice.models.Make;
import com.telerikacademy.carservice.models.Models;
import com.telerikacademy.carservice.repository.CustomerCarsRepository;
import com.telerikacademy.carservice.service.CustomerCarsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerCarsServiceImplTest {
    @Mock
    CustomerCarsRepository customerCarsRepository;
    @InjectMocks
    CustomerCarsServiceImpl customerCarsServiceImpl;
    CustomerCars car = new CustomerCars();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCustomerCars() {
        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCars();
        Assert.assertEquals(Arrays.<CustomerCars>asList(
                new CustomerCars(new Customer("email", "phone", "name", 0),
                        new Models(new Make("makeName"),
                                "modelName"), 2001, "licensePlate", "vinNumber"),
                new CustomerCars(new Customer("email1", "phone1", "name1", 0),
                        new Models(new Make("makeName1"),
                                "modelName1"), 2000, "licensePlate1", "vinNumber1")),
                result);
    }

    @Test
    public void testGetAllCustomerCarsByCustomerId() {
        when(customerCarsRepository.findCustomerCarsByCustomer_CustomerId(anyLong())).thenReturn(Arrays.<CustomerCars>asList(new CustomerCars(new Customer("email", "phone", "name", 0), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber")));

        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCarsByCustomerId(Long.valueOf(1));
        Assert.assertEquals(Arrays.<CustomerCars>asList(new CustomerCars(new Customer("email", "phone", "name", 0), new Models(new Make("makeName"), "modelName"), Integer.valueOf(0), "licensePlate", "vinNumber")), result);
    }
}

