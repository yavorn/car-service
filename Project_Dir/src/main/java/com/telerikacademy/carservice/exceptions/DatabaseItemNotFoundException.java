package com.telerikacademy.carservice.exceptions;

public class DatabaseItemNotFoundException extends RuntimeException {
    public DatabaseItemNotFoundException(int id) {
        super(String.format("Customer with ID %d not found.", id));
    }

    public DatabaseItemNotFoundException(String username) {
        super(String.format("Customer with username %s not found.", username));

    }
}
