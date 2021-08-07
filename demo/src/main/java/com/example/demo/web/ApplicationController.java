package com.example.demo.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class ApplicationController {
    @GetMapping("/")
    public String home(Principal principal, Model model) {
        model.addAttribute("user", principal);
        return "index"; //Html file name
    }

}
