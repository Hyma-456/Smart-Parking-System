package com.smartparking.authservice.service;

import com.smartparking.authservice.model.User;
import com.smartparking.authservice.repository.UserRepository;
import com.smartparking.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        return userRepository.save(user);
    }

    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getUserId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        return response;
    }
}
