package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.controllers.exception_handler.EmailValidator;
import com.telerikacademy.carservice.service.contracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        EmailValidator.validateSimpleMessage(message);
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessageForPasswordResetUsingTemplate(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        EmailValidator.validateSimpleMessage(message);
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessageUsingTemplateWhenCreatingCustomer(String to, SimpleMailMessage template, String... templateArgs) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text = String.format(template.getText(), templateArgs);

        message.setTo(to);
        message.setText(text);

        EmailValidator.validateSimpleMessage(message);
        emailSender.send(message);
    }
}
