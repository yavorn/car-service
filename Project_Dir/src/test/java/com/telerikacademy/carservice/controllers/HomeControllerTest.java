package com.telerikacademy.carservice.controllers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomeControllerTest {
    private HomeController homeController = new HomeController();

    @Test
    public void showHomePage_ShouldReturn_WhenValidArgsPassed() {
        String result = homeController.showHomePage();
        assertEquals("index", result);
    }

    @Test
    public void showAdminPortal_ShouldReturn_WhenValidArgsPassed() {
        String result = homeController.showAdminPortal();
        assertEquals("admin-portal", result);
    }

    @Test
    public void showAccessDenied_ShouldReturn_WhenValidArgsPassed() {
        String result = homeController.showAccessDenied();
        assertEquals("access-denied", result);
    }
}

