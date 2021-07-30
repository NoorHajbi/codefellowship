package com.example.demo.web;

import org.springframework.web.bind.annotation.GetMapping;

public class ApplicationController {
    @GetMapping("/")
    public String home() {
        return "index"; //Html file name
    }
}
