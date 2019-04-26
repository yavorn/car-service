package com.telerikacademy.carservice.exceptions;

public class UserRightsNotDisabledException extends RuntimeException {
    public UserRightsNotDisabledException(String username) {
        super(String.format("%s has active rights!", username));
    }
}
