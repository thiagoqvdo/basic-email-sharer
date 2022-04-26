package com.thiagoqvdo.cloudnative.temafinal1.email.services;

import com.thiagoqvdo.cloudnative.temafinal1.email.exceptions.InvalidParamsException;
import com.thiagoqvdo.cloudnative.temafinal1.email.entities.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Field;
import java.util.List;

@Service("service")
public class EmailService {
    @Autowired
    @Qualifier("session")
    private Session session;

    @Autowired
    @Qualifier("localEmailAddress")
    private InternetAddress localEmailAddress;

    public void sendEmail(EmailRequest emailProperties) {
        List<Field> emailFields = List.of(emailProperties.getClass().getDeclaredFields());
        emailFields.forEach(emailField -> {
            try {
                emailField.setAccessible(true);
                if (emailField.get(emailProperties) == null || emailField.get(emailProperties).toString().isBlank()) {
                    emailField.setAccessible(false);
                    throw new InvalidParamsException("Email " + emailField.getName() + " is invalid.");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(localEmailAddress);
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailProperties.getRecipient()));
            msg.setSubject(emailProperties.getTitle());
            msg.setText(emailProperties.getMessage());

            Transport.send(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
