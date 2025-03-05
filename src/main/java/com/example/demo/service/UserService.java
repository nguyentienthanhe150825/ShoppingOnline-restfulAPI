package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.domain.response.ResUserDTO;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    // Dependency Inject: Constructor
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User createUser) {
        return this.userRepository.save(createUser);
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setPhone(user.getPhone());
        res.setGender(user.getGender());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        res.setCreateAt(user.getCreateAt());
        
        return res;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User handleGetUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }
}
