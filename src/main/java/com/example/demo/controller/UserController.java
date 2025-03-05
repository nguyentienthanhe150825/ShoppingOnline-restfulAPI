package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    // Dependency Inject: Constructor
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
}
