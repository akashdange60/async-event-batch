package com.async_event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootAsyncEventBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAsyncEventBatchApplication.class, args);
	}

}
