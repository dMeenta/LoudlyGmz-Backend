package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.application.dto.user.UserRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserByUid(String uid);
}
