package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpsController {

    @GetMapping("/https")
    public String httpsReq() {
        return "https request";
    }

    @GetMapping("/https/hello")
    public String httpsHello() {
        return "https hello";
    }

    @GetMapping("/http")
    public String httpReq() {
        return "http request";
    }

    @GetMapping("/http/hello")
    public String httpHello() {
        return "http hello";
    }
}
