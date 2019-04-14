package com.telerikacademy.carservice.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    @NotNull(message = "Username is mandatory.")
    @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters")
    private String username;

    @NotNull(message = "Username is mandatory.")
    @Size(min = 5, max = 10, message = "Password must be between 5 and 10 characters")
    private String password;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
