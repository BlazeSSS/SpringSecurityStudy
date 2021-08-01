package com.example.springsecurity.controller;

import com.example.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class RedisSessionController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/set")
    public String set(HttpSession session) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("name", principal.getUsername());
        return String.valueOf(port);
    }

    @GetMapping("/get")
    public String get(HttpSession session) {
        return session.getAttribute("name") + ":" + port;
    }
}
