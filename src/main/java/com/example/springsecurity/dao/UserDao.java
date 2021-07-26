package com.example.springsecurity.dao;

import com.example.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
