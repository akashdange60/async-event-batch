package com.async_event.serviceImpl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.async_event.event.UserRegisteredEvent;
import com.async_event.model.User;
import com.async_event.repository.UserRepository;
import com.async_event.service.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface{
	
	private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    public UserService(UserRepository repository,
                       ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public void registerUser(User user) {
        repository.save(user);
        publisher.publishEvent(new UserRegisteredEvent(user));
    }

}
