package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private int customerId;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "customerPassword")
    @NotNull(message = "Password is mandatory.")
    @Size(min = 5, max = 10, message = "Password must be between 5 and 10 characters")
    private String customerPassword;

    @Column(name = "phone")
    @Size(min = 10, max = 14, message = "Phone number must be between 10 and 14 characters")
    private String phone;

    @Column(name = "name")
    @Size(min = 5, max = 25, message = "Name must be bewteen 5 and 25 characters.")
    private String name;

    public Customer(){}

    public Customer(String email, String customerPassword, String phone, String name) {
        this.email = email;
        this.customerPassword = customerPassword;
        this.phone = phone;
        this.name = name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
}
