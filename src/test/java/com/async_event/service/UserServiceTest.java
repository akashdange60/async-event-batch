package com.async_event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.async_event.model.User;
import com.async_event.repository.UserRepository;
import com.async_event.serviceImpl.UserService;

@SpringBootTest
public class UserServiceTest {
	
	 @Autowired
	    private UserService userService;

	    @Autowired
	    private UserRepository userRepository;

	    @Test
	    void shouldRegisterUser() {
	        User user = new User();
	        user.setName("Akash");
	        user.setEmail("akash@gmail.com");

	        userService.registerUser(user);

	        assertEquals(1, userRepository.count());
	    }

}
