	First of all need to create Database in MySQL as we mention in application.properties file
create database async_event;

	       ***********     Flow Chart    **********
springboot-async-event-batch-demo
│
├── pom.xml
├── README.md
│
└── src
    └── main
        ├── java
        │   └── com.example.onboarding
        │       │
        │       ├── SpringBootAsyncApplication.java
        │       │
        │       ├── config
        │       │   ├── AsyncConfig.java
        │       │   ├── BatchConfig.java
        │       │   └── MailConfig.java
        │       │
        │       ├── controller
        │       │   └── UserController.java
        │       │
        │       ├── service
        │       │   ├── UserService.java
        │       │   ├── EmailService.java
        │       │
        │       ├── event
        │       │   ├── UserRegisteredEvent.java
        │       │   └── UserEventListener.java
        │       │
        │       ├── batch
        │       │   ├── scheduler
        │       │   │   └── ReportScheduler.java
        │       │   │
        │       │   ├── reader
        │       │   │   └── UserItemReader.java
        │       │   │
        │       │   ├── writer
        │       │   │   └── UserItemWriter.java
        │       │   │
        │       │   └── model
        │       │       └── UserReport.java
        │       │
        │       ├── repository
        │       │   └── UserRepository.java
        │       │
        │       ├── entity
        │       │   └── User.java
        │       │
        │       └── exception
        │           └── GlobalExceptionHandler.java
        │
        └── resources
            ├── application.properties
            ├── schema.sql
            └── data.sql




	#######   Spring Boot Async Event Batch Demo   ####

	              ## Architecture Diagram ##

[Client]
   |
   v
[REST Controller]
   |
   v
[@Transactional Service]
   |
   v
[Spring Event Publisher]
   |
   v
[@Async Event Listener] ---> [ThreadPoolTaskExecutor]
   |
   +--> Gmail Notification
   +--> Audit / Metrics

[Nightly Scheduler]
   |
   v
[Spring Batch Job]

[Metrics Scheduler]
   |
   v
[Actuator] --> JVM Metrics --> Email Attachment

## Features
- MySQL + JPA
- Async event-driven architecture
- Gmail SMTP integration
- Spring Batch with Scheduler
- JVM metrics via Actuator
- Daily metrics email report
- Swagger API documentation
- Global exception handling
- JUnit testing

## URLs
- Swagger: /swagger-ui.html
- Actuator: /actuator/health
- Metrics: /actuator/metrics

## Run
mvn spring-boot:run


	



	
	