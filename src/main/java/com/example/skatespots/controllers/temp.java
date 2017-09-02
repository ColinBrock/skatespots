package com.example.skatespots.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class temp {

    @GetMapping("/home")
    public String home() {
        return "temp/home";
    }

    @GetMapping("/login")
    public String login() {
        return "temp/login";
    }

    @GetMapping("/hello")
    public String hello() {
        return "temp/hello";
    }
}
