package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.domain.response.ResCreateUserDTO;
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

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setPhone(user.getPhone());
        res.setGender(user.getGender());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        res.setCreateAt(user.getCreateAt());
        
        return res;
    }
}
