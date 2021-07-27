package com.example.demo.web;

import org.springframework.web.bind.annotation.GetMapping;

public class ApplicationController {
    @GetMapping
    public String getHomePage() {
        return "home";
    }
}
