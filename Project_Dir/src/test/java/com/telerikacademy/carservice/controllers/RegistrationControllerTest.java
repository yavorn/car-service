package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CustomerDto;
import com.telerikacademy.carservice.service.contracts.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

public class RegistrationControllerTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    RegistrationController registrationController;
    private Model model = new ConcurrentModel();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showRegister_ShouldReturn_WhenValidArgsPassed() {
        String result = registrationController.showRegister(model);
        Assert.assertEquals("customer", result);
    }

    @Test
    public void testRegisterNewCustomer() throws Exception {
        String result = registrationController.registerNewCustomer(new CustomerDto());
        Assert.assertEquals("new-customer-confirmation", result);
    }

    @Test
    public void testDisableCustomer() throws Exception {
        String result = registrationController.disableCustomer(new CustomerDto());
        Assert.assertEquals("redirect:admin-portal", result);
    }

    @Test
    public void testEnableCustomer() throws Exception {
        String result = registrationController.enableCustomer(new CustomerDto());
        Assert.assertEquals("redirect:admin-portal", result);
    }

    @Test
    public void testShowAdminRegister() throws Exception {
        String result = registrationController.showAdminRegister(model);
        Assert.assertEquals("admin", result);
    }

    @Test
    public void testRegisterAdministrator() throws Exception {
        String result = registrationController.registerAdministrator(new CustomerDto());
        Assert.assertEquals("new-customer-confirmation", result);
    }
}