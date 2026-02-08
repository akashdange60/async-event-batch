package com.async_event.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message) {
        super(message);
    }

}
