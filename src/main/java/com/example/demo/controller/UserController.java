package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.domain.response.user.ResUpdateUserDTO;
import com.example.demo.domain.response.user.ResUserDTO;
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
    public ResponseEntity<ResUserDTO> createNewUser(@Valid @RequestBody User createUser) throws InvalidException {
        // check email exist in database
        boolean isEmailExist = this.userService.checkEmailExist(createUser.getEmail());
        if (isEmailExist == true) {
            throw new InvalidException("Email: " + createUser.getEmail() + " is exist");
        }

        User newUser = this.userService.handleCreateUser(createUser);
        ResUserDTO userDTO = this.userService.convertToResUserDTO(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws InvalidException {
        User user = this.userService.handleGetUserById(id);
        if (user == null) {
            throw new InvalidException("User with id = " + id + " not exist");
        }
        ResUserDTO userDTO = this.userService.convertToResUserDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User requestUser) throws InvalidException {
        User userUpdate = this.userService.handleUpdateUser(requestUser);
        if (userUpdate == null) {
            throw new InvalidException("User with id = " + requestUser.getId() + " not exist");
        }
        
        ResUpdateUserDTO userDTO = this.userService.convertToResUpdateUserDTO(userUpdate);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
    }
}
