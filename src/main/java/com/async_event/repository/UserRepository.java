package com.async_event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.async_event.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
