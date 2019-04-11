package com.telerikacademy.carservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 symbols.")
    private String username;

    @Column(name = "password")
    @Size(min = 5, max = 10, message = "Password must be between 5 and 10 symbols.")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    public User(){}

    public User(String username, String password, int enabled){
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

}
