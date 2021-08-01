package com.example.springsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(HttpServletRequest request, HttpSession session, Authentication authentication) throws Exception {
//        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        return new ObjectMapper().writeValueAsString(details)
                + "<br>RequestedSessionId: " + request.getRequestedSessionId();
    }

    @RequestMapping("/hello/{id}")
    public String helloId(@PathVariable Integer id, @MatrixVariable String name) {
        return "id = " + id + "; name = " + name;
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

    @GetMapping({"/", "/index"})
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
