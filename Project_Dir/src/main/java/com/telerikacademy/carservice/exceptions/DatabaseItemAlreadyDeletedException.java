package com.telerikacademy.carservice.exceptions;

public class DatabaseItemAlreadyDeletedException extends RuntimeException {
    public DatabaseItemAlreadyDeletedException(String message) {
        super(message);
    }
}
