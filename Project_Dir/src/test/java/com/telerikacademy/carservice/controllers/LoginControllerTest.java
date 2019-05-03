package com.telerikacademy.carservice.controllers;

import org.junit.Assert;
import org.junit.Test;

public class LoginControllerTest {
    LoginController loginController = new LoginController();

    @Test
    public void testShowLogin() {
        String result = loginController.showLogin();
        Assert.assertEquals("login", result);
    }
}
