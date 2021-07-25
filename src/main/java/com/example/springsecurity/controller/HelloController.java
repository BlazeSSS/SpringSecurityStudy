package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("user/hello")
    public String user() {
        return "user";
    }

    @RequestMapping("/byebye")
    public String byebye() {
        return "byebye";
    }

    @RequestMapping("/index")
    public String index() {
        return "主页";
    }
}
