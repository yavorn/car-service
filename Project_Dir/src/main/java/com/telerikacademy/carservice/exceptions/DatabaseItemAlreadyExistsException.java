package com.telerikacademy.carservice.exceptions;

public class DatabaseItemAlreadyExistsException extends RuntimeException {
    public DatabaseItemAlreadyExistsException(String message) {
        super (message);
    }
}
