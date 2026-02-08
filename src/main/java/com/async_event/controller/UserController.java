package com.async_event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.async_event.batch.scheduler.MetricsMailScheduler;
import com.async_event.model.MetricsMailAudit;
import com.async_event.model.User;
import com.async_event.repository.MetricsMailAuditRepository;
import com.async_event.service.UserServiceInterface;
import com.async_event.serviceImpl.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServiceInterface service;
	
	@Autowired
	private MetricsMailScheduler scheduler;
	
	@Autowired
	MetricsMailAuditRepository metricsMailAuditRepo;

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
    
//    Controller to download stored files for Json 
    
    @GetMapping("/metrics/{id}/json")
    public ResponseEntity<byte[]> downloadJson(@PathVariable Long id) {

    	MetricsMailAudit audit = metricsMailAuditRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + audit.getJsonFileName())
                .contentType(MediaType.parseMediaType(audit.getJsonContentType()))
                .body(audit.getJsonMetrics());
    }

}
