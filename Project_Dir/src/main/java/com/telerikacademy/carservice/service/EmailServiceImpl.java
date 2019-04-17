package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.service.contracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.getMessage();
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplateWhenCreatingCustomer(String to, SimpleMailMessage template, String... templateArgs) {
            SimpleMailMessage message = new SimpleMailMessage();
            String text = String.format(template.getText(), templateArgs);

            message.setTo(to);
            message.setFrom("Team 6 Car Service");
            message.setText(text);
            emailSender.send(message);
    }
}
