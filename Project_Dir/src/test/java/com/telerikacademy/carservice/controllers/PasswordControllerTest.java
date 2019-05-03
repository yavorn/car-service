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

public class PasswordControllerTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    PasswordController passwordController;
    private Model model = new ConcurrentModel();
    private CustomerDto customerDto = new CustomerDto();
    private CustomerDto customerDtoWithWrongPassConfirm = new CustomerDto();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerDto.setPassword("password");
        customerDto.setPasswordConfirmation("password");

        customerDtoWithWrongPassConfirm.setPassword("password");
        customerDtoWithWrongPassConfirm.setPasswordConfirmation("pass");
    }

    @Test
    public void showChangeOrResetPassword_ShouldReturn_WhenValidArgsPassed() {
        String result = passwordController.showChangeOrResetPassword(model);
        Assert.assertEquals("password", result);
    }

    @Test
    public void resetPassword_ShouldReturn_WhenValidArgsPassed() {
        String result = passwordController.resetPassword(new CustomerDto(), model);
        Assert.assertEquals("password-confirmation", result);
    }

    @Test
    public void changePassword_ShouldReturn_WhenValidArgsPassed() {
        String result = passwordController.changePassword(customerDto, model);
        Assert.assertEquals("password-confirmation", result);
    }

    @Test
    public void changePassword_ShouldReturn_WhenInvalidArgsPassed() {
        String result = passwordController.changePassword(customerDtoWithWrongPassConfirm, model);
        Assert.assertEquals("password", result);
    }
}
