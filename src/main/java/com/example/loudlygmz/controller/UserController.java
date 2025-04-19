package com.example.loudlygmz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.entity.User;
import com.example.loudlygmz.services.User.IUserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
}
