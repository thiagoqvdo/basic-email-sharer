package com.thiagoqvdo.cloudnative.temafinal1.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.thiagoqvdo.cloudnative.temafinal1.email.exceptions.InvalidParamsException;
import com.thiagoqvdo.cloudnative.temafinal1.email.entities.EmailRequest;
import com.thiagoqvdo.cloudnative.temafinal1.email.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.mail.Session;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class EmailServiceTest {

    @Autowired
    @InjectMocks
    @Qualifier("service")
    private EmailService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void contextLoad() {
        assertThat(service).isNotNull();
    }

    @Test
    public void shouldSendEmail() {
        GreenMail mailServer = new GreenMail();
        mailServer.start();

        Properties properties = new Properties();
        properties.put("mail.smtp.port", mailServer.getSmtp().getPort());

        Session mailSession = Session.getInstance(properties);
        service.setSession(mailSession);

        service.sendEmail(EmailRequest.builder()
                .recipient("recipientTest")
                .title("titleTest")
                .message("messageTest")
                .build());
        assertEquals("messageTest", GreenMailUtil.getBody(mailServer.getReceivedMessages()[0]));
        mailServer.stop();
    }

    @Test
    public void shouldReturnInvalidParamsIfEmailRequestContainsBlankFields() {
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient("test")
                .title("")
                .message("test")
                .build()));
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient("")
                .title("test")
                .message("test")
                .build()));
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient("test")
                .title("test")
                .message("")
                .build()));
    }

    @Test
    public void shouldReturnInvalidParamsIfEmailRequestContainsNullFields() {
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient(null)
                .title("")
                .message("test")
                .build()));
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient("")
                .title(null)
                .message("test")
                .build()));
        assertThrows(InvalidParamsException.class, ()->service.sendEmail(EmailRequest.builder()
                .recipient("test")
                .title("test")
                .message(null)
                .build()));
    }
}
