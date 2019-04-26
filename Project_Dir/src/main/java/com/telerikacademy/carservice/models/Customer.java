package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone")
    @Size(min = 10, max = 14, message = "Phone number must be between 10 and 14 characters")
    private String phone;

    @Column(name = "name")
    @Size(min = 5, max = 25, message = "Name must be bewteen 5 and 25 characters.")
    private String name;

    @Column(name = "is_deleted")
    private int isDeleted;

    public Customer() {
    }

    public Customer(String email, String phone, String name, int isDeleted) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
