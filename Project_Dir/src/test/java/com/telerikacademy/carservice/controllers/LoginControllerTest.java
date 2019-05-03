package com.telerikacademy.carservice.controllers;

import org.junit.Assert;
import org.junit.Test;

public class LoginControllerTest {
    LoginController loginController = new LoginController();

    @Test
    public void showLogin_ShouldReturn_WhenValidArgsPassed() {
        String result = loginController.showLogin();
        Assert.assertEquals("login", result);
    }
}
