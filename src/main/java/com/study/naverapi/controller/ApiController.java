package com.study.naverapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApiController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
