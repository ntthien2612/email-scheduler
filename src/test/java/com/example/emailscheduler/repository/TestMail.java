package com.example.emailscheduler.repository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestMail {
    private final JavaMailSender mailSender;

    public void sendTest() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("ntthien.work.2612@gmail.com");
        msg.setSubject("Test");
        msg.setText("This is a test email");
        mailSender.send(msg);
        System.out.println("Test email sent!");
    }
}