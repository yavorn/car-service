package com.telerikacademy.carservice.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(value = "Homepage management", description = "Contains the methods for managing the homepage, admin-portal and access-denied page.")
public class HomeController {

    @ApiOperation(value = "Returns homepage.")
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @ApiOperation(value = "Returns admin portal.")
    @GetMapping("/admin-portal")
    public String showAdminPortal() {
        return "admin-portal";
    }

    @ApiOperation(value = "Returns access denied page.")
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
