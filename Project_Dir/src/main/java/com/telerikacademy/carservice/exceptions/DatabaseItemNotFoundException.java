package com.telerikacademy.carservice.exceptions;

public class DatabaseItemNotFoundException extends RuntimeException {
    public DatabaseItemNotFoundException(String item,Long id) {
        super(String.format("%s with ID - %d not found.",item, id));
    }

    public DatabaseItemNotFoundException(String message) {
        super(message);

    }
}
