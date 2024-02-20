package com.tiktok.tiktok.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tiktok.tiktok.entity.User;
import com.tiktok.tiktok.service.UserService;

import lombok.AllArgsConstructor;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.authenticate(user));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
    
    
}
