package com.example.loudlygmz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.entity.User;
import com.example.loudlygmz.services.User.IUserService;
import com.example.loudlygmz.utils.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/profile/{uid}")
    public ResponseEntity<ApiResponse<?>> getProfile(@PathVariable String uid ) {
        return userService.getProfile(uid);
    }
}
