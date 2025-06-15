package com.learnandcode.news_aggregator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ControllerHealthCheck {
    @GetMapping
    public String healthCheck() {
        return "Server is running";
    }
}
