package com.telerikacademy.carservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/admin")
    public String showAdminPortal() {
        return "admin";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
