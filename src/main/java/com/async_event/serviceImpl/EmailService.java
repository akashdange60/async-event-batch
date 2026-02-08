package com.async_event.serviceImpl;

import java.util.concurrent.CompletableFuture;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.async_event.model.User;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async("taskExecutor")
    public CompletableFuture<Boolean> sendWelcomeEmail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome!");
        message.setText("Hello " + user.getName() + ", welcome!");

        mailSender.send(message);
        return CompletableFuture.completedFuture(true);
    }

}
