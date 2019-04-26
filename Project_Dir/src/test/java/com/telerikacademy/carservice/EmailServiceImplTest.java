package com.telerikacademy.carservice;

import com.telerikacademy.carservice.exceptions.EmailNotSentException;
import com.telerikacademy.carservice.service.EmailServiceImpl;
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
    EmailServiceImpl emailService;
    private String to = "some.email@test.moc";
    private String subject = "Mockito test email";
    private String text = "Mockito text for testing email service.";
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendSimpleMessage_Should_WhenValidArgsPassed() throws Exception {
        emailService.sendSimpleMessage(to, subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test(expected = EmailNotSentException.class)
    public void testSendSimpleMessage_ShouldThrow_WhenInvalidArgsPassed() throws Exception {
        emailService.sendSimpleMessage("", subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test
    public void testSendSimpleMessageForPasswordReset_Should_WhenValidArgsPassed() throws Exception {
        emailService.sendSimpleMessageForPasswordResetUsingTemplate(to, subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }

    @Test(expected = EmailNotSentException.class)
    public void testSendSimpleMessageForPasswordReset_ShouldThrow_WhenInvalidArgsPassed() throws Exception {
        emailService.sendSimpleMessageForPasswordResetUsingTemplate("", subject, text);

        verify(emailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
    }
    @Test(expected = EmailNotSentException.class)
    public void sendSimpleMessage_ShouldThrow_WhenInvalidArgsPassedInToField() {
        emailService.sendSimpleMessage("", subject, text);
    }

    @Test(expected = EmailNotSentException.class)
    public void sendSimpleMessage_ShouldThrow_WhenInvalidArgsPassedInTextField() {
        emailService.sendSimpleMessage(to, "", text);
    }

    @Test(expected = EmailNotSentException.class)
    public void sendSimpleMessage_ShouldThrow_WhenInvalidArgsPassedInSubjectField() {
        emailService.sendSimpleMessage(to, subject, "");
    }
}
