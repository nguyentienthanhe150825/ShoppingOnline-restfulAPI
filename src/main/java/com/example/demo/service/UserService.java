package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    // Dependency Inject: Constructor
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
