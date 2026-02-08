package com.async_event.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.async_event.serviceImpl.EmailService;

@Component
public class UserEventListener {
	
	private final EmailService emailService;
	
	public UserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("taskExecutor")
    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        emailService.sendWelcomeEmail(event.getUser());
    }

}
