package com.telerikacademy.carservice.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(value = "Login page management", description = "Contains the method for returning the login page.")
public class LoginController {

    @ApiOperation(value = "Returns login page.")
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
}
