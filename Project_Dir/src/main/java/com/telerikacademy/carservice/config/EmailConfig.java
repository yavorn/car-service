package com.telerikacademy.carservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig {

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Welcome to Team 6 Car Service");
        message.setText(
                        "Dear %s,\n\n"+
                        "Thank you for choosing our services!\n" +
                        "You can login to our system with the following credentials:\n" +
                        "Username: %s\n" +
                        "Password: %s\n\n" +
                        "Best Regards,\n" +
                        "Team 6 Car Service\n");
        return message;
    }
}
