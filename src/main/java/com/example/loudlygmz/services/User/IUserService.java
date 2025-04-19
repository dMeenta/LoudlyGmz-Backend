package com.example.loudlygmz.services.User;

import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.entity.User;

public interface IUserService {
    ResponseEntity<?> createUser(User user);
}
