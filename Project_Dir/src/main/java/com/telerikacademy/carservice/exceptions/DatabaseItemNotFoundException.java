package com.telerikacademy.carservice.exceptions;

public class DatabaseItemNotFoundException extends RuntimeException {
    public DatabaseItemNotFoundException(String message) {
        super(message);
    }
}
