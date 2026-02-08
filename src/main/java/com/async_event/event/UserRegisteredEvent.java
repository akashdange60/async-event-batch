package com.async_event.event;

import com.async_event.model.User;

public class UserRegisteredEvent {
	
	private final User user;

    public UserRegisteredEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
