package com.Lostfound.lostfound.service;

import com.Lostfound.lostfound.Model.user;
import com.Lostfound.lostfound.Repository.userRepository;
import com.Lostfound.lostfound.Controller.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already registered";
        }

        user user = new user();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        return "User registered successfully";
    }

    public String loginUser(String email, String password) throws Exception {
        user user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        // Use JwtUtil to generate token
        return jwtUtil.generateToken(user);
    }

    public List<user> getAllUsers() {
        return userRepository.findAll();
    }
}
