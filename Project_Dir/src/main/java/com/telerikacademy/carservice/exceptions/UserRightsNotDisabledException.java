package com.telerikacademy.carservice.exceptions;

public class UserRightsNotDisabledException extends RuntimeException {
    public UserRightsNotDisabledException(String message) {
        super(message);
    }
}
