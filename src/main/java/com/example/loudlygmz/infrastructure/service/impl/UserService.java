package com.example.loudlygmz.infrastructure.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.UserRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.Role;
import com.example.loudlygmz.domain.model.User;
import com.example.loudlygmz.domain.repository.UserRepository;
import com.example.loudlygmz.domain.service.IUserService;
import com.example.loudlygmz.infrastructure.common.SanitizationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    
    @Override
    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setUid(request.getUid());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setBiography(request.getBiography());
        user.setProfilePicture(request.getProfilePicture());
        user.setRole(Role.USER);
        user.setCreationDate(LocalDateTime.now());

        user = SanitizationUtils.sanitizeUser(user);

        userRepository.save(user);

        return new UserResponse(
            user.getUid(),
            user.getUsername(),
            user.getEmail(),
            user.getBiography(),
            user.getProfilePicture(),
            user.getRole().name(),
            user.getCreationDate()
            );
    }

    @Override
    public UserResponse getUserByUid(String uid) {
        User user = userRepository.findById(uid)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        
        return new UserResponse(
            user.getUid(),
            user.getUsername(),
            user.getEmail(),
            user.getBiography(),
            user.getProfilePicture(),
            user.getRole().name(),
            user.getCreationDate()
        );
    }
}
