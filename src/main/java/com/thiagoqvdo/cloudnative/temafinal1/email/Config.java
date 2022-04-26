package com.thiagoqvdo.cloudnative.temafinal1.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

@Configuration
public class Config {

    @Value("${email.address}")
    private String emailAddress;
    @Value("${email.password}")
    private String emailPass;

    @Bean("session")
    public Session getSession() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            return Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAddress, emailPass);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean("localEmailAddress")
    public InternetAddress getLocalEmailAddress() {
        try {
            return new InternetAddress(emailAddress);
        } catch (AddressException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
