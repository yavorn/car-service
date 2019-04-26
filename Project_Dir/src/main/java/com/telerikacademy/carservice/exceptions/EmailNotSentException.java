package com.telerikacademy.carservice.exceptions;

import org.springframework.mail.MailException;

public class EmailNotSentException extends MailException {

    public EmailNotSentException(String message) {
        super(message);
    }
}

