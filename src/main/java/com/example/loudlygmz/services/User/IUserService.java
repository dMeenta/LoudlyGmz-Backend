package com.example.loudlygmz.services.User;

import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.entity.User;
import com.example.loudlygmz.utils.ApiResponse;

public interface IUserService {
    ResponseEntity<?> createUser(User user);
    ResponseEntity<ApiResponse<?>> getProfile(String uid);
}
