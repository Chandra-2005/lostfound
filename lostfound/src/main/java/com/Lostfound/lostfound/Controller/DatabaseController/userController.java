package com.Lostfound.lostfound.Controller.DatabaseController;

import com.Lostfound.lostfound.Model.user;
import com.Lostfound.lostfound.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class userController {

    @Autowired
    private UserService userService;

    // DTO for registration
    public static class RegisterRequest {
        public String username;
        public String email;
        public String password;
    }

    // DTO for login
    public static class LoginRequest {
        public String email;
        public String password;
    }

    // ➕ Register user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            String message = userService.registerUser(request.username, request.email, request.password);
            return ResponseEntity.ok(Map.of(
                    "status", message.startsWith("✅") ? "success" : "error",
                    "message", message
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            String token = userService.loginUser(request.email, request.password);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    // 📋 Get all users (admin)
    @GetMapping("/all")
    public ResponseEntity<List<user>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
