package com.example.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CsrfController {

    @PostMapping("/transfer")
    @ResponseBody
    public String transferMoney(String name, Integer money) {
        return "name: " + name + "; money: " + money;
    }

    @PostMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/helloPage")
    public String helloPage() {
        return "hello";
    }
}
