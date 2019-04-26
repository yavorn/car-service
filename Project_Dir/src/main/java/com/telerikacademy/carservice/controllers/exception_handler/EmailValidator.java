package com.telerikacademy.carservice.controllers.exception_handler;

import com.telerikacademy.carservice.exceptions.EmailNotSentException;
import org.springframework.mail.SimpleMailMessage;

public class EmailValidator {

    public static void validateSimpleMessage(SimpleMailMessage simpleMailMessage) {
        if ("".equalsIgnoreCase(simpleMailMessage.getText())) {
            throw new EmailNotSentException("Message Text should not be empty");
        }

        if (simpleMailMessage.getTo() == null) {
            throw new EmailNotSentException("Message To should not be empty");
        }

        for (String to : simpleMailMessage.getTo()) {
            if (to.equalsIgnoreCase("")) {
                throw new EmailNotSentException("Message To should not be empty");
            }
        }

        if ("".equalsIgnoreCase(simpleMailMessage.getSubject())) {
            throw new EmailNotSentException("Message Subject should not be empty");
        }
    }
}
