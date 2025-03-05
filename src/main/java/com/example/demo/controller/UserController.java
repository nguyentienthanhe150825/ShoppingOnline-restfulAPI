package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.domain.response.ResCreateUserDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.exception.InvalidException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    // Dependency Inject: Constructor
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User createUser) throws InvalidException {
        // check email exist in database
        boolean isEmailExist = this.userService.checkEmailExist(createUser.getEmail());
        if (isEmailExist == true) {
            throw new InvalidException("Email: " + createUser.getEmail() + " is exist");
        }

        User newUser = this.userService.handleCreateUser(createUser);
        ResCreateUserDTO userDTO = this.userService.convertToResCreateUserDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
