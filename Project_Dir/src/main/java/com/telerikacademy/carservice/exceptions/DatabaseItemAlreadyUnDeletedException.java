package com.telerikacademy.carservice.exceptions;

public class DatabaseItemAlreadyUnDeletedException extends RuntimeException {
    public DatabaseItemAlreadyUnDeletedException(String message) {
        super(message);
    }
}
