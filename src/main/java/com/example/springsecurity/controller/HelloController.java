package com.example.springsecurity.controller;

import com.example.springsecurity.config.authentication.details.MyWebAuthenticationDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(Authentication authentication) throws Exception {
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        return new ObjectMapper().writeValueAsString(details);
    }

    @GetMapping("admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("user/hello")
    public String user() {
        return "user";
    }

    @GetMapping("/byebye")
    public String byebye() {
        return "byebye";
    }

    @GetMapping("/index")
    public String index() {
        return "主页";
    }

    @GetMapping("fullyAuth")
    public String fullyAuth() {
        return "fullyAuth";
    }

    @GetMapping("/rememberme")
    public String rememberme() {
        return "remember-me";
    }
}
