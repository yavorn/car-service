package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class CustomerCarsServiceImplTest {
    @Mock
    CustomerCarsRepository customerCarsRepository;
    @InjectMocks
    private CustomerCarsServiceImpl customerCarsServiceImpl;
    private CustomerCars testCarOne = new CustomerCars();
    private CustomerCars testCarTwo = new CustomerCars();
    private Customer customer = new Customer();
    private Models model = new Models();
    private List<CustomerCars> carsList = new ArrayList();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testCarOne.setCustomer(customer);
        testCarOne.setLicensePlate("plateNo");
        testCarOne.setVinNumber("vinNo");
        testCarOne.setYearOfProduction(2000);
        testCarOne.setModel(model);

        testCarTwo.setCustomer(customer);
        testCarTwo.setLicensePlate("plateNo2");
        testCarTwo.setVinNumber("vinNo2");
        testCarTwo.setYearOfProduction(2000);
        testCarTwo.setModel(model);

        carsList.add(testCarOne);
        carsList.add(testCarTwo);
    }

    @Test
    public void getAllCustomerCars_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findAllByCustomerCarDeletedFalse()).thenReturn(carsList);
        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCars();
        assertEquals(2, result.size());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getAllCustomerCars_ShouldThrow_WhenNoCarsFound() {
        when(customerCarsRepository.findAll()).thenReturn(new ArrayList<CustomerCars>());
        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCars();
        assertEquals(1, result.size());
    }

    @Test
    public void getAllCustomerCarsByCustomerId_ShouldReturn_WhenValidArgsPassed() {
        when(customerCarsRepository.findCustomerCarsByCustomer_CustomerIdAndCustomerCarDeletedFalse(1L)).thenReturn(carsList);

        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCarsByCustomerId(1L);
        assertEquals(2, result.size());
    }

    @Test(expected = DatabaseItemNotFoundException.class)
    public void getAllCustomerCarsByCustomerId_ShouldThrow_WhenNoCarsFound() {
        when(customerCarsRepository.findCustomerCarsByCustomer_CustomerIdAndCustomerCarDeletedFalse(1L)).thenReturn(new ArrayList<CustomerCars>());
        List<CustomerCars> result = customerCarsServiceImpl.getAllCustomerCarsByCustomerId(1L);
        assertEquals(1, result.size());
    }
}

