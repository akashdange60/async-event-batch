package com.async_event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.async_event.batch.scheduler.MetricsMailScheduler;
import com.async_event.model.User;
import com.async_event.service.UserServiceInterface;
import com.async_event.serviceImpl.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServiceInterface service;
	
	@Autowired
	private MetricsMailScheduler scheduler;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        service.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
    @GetMapping("/test/metrics-mail")
    public String triggerMail() {
        scheduler.sendMetricsMail();
        return "Metrics mail triggered. Check inbox.";
    }

}
