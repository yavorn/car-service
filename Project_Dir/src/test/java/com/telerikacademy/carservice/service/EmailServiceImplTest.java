package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.exceptions.EmailNotSentException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceImplTest {
    @Mock
    JavaMailSender emailSender;
    @InjectMocks
    EmailServiceImpl emailServiceImpl;
    private String to = "some.email@abv.bg";
    private String subject = "Mockito test email";
    private String text = "Mockito text for testing email service.";
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendSimpleMessage_Should_WhenValidArgsPassed() throws Exception {
        emailServiceImpl.sendSimpleMessage(to, subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test(expected = EmailNotSentException.class)
    public void testSendSimpleMessage_ShouldThrow_WhenInvalidArgsPassed() throws Exception {
        emailServiceImpl.sendSimpleMessage("", subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    public void testSendSimpleMessageForPasswordResetUsingTemplate() throws Exception {
        emailServiceImpl.sendSimpleMessageForPasswordResetUsingTemplate("to", "subject", "text");
    }

    @Test
    public void testSendSimpleMessageUsingTemplateWhenCreatingCustomer() throws Exception {
        emailServiceImpl.sendSimpleMessageUsingTemplateWhenCreatingCustomer("to", null, "templateArgs");
    }
}
