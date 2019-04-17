package com.telerikacademy.carservice.service.contracts;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendSimpleMessageUsingTemplateWhenCreatingCustomer(String to, SimpleMailMessage template, String... templateArgs);
}
