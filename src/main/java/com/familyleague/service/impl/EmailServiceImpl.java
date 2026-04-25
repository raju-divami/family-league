package com.familyleague.service.impl;

import com.familyleague.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Email service implementation using JavaMailSender.
 * Requires spring.mail.* properties configured in application.properties.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            log.debug("Sending email to: {}", to);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
            
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            // Don't throw exception to prevent breaking the calling flow
        }
    }

    @Async
    @Override
    public void sendEmailAsync(String to, String subject, String body) {
        sendEmail(to, subject, body);
    }
}
