package com.telerikacademy.carservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameExistsException extends Exception {
    public UsernameExistsException(String message) {
        super(message);
    }
}
