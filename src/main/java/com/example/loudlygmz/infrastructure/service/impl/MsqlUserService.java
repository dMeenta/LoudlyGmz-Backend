package com.example.loudlygmz.infrastructure.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.MsqlUserRequest;
import com.example.loudlygmz.application.dto.user.MsqlUserResponse;
import com.example.loudlygmz.domain.enums.Role;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.repository.IMsqUserlRepository;
import com.example.loudlygmz.domain.service.IMsqlUserService;
import com.example.loudlygmz.infrastructure.common.SanitizationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MsqlUserService implements IMsqlUserService {

    private final IMsqUserlRepository msqUserlRepository;

    @Override
    public MsqlUserResponse createUser(MsqlUserRequest request) {
        MsqlUser user = new MsqlUser();
        user.setUid(request.getUid());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setBiography(request.getBiography());
        user.setProfilePicture(request.getProfilePicture());
        user.setRole(Role.USER);
        user.setCreationDate(LocalDateTime.now());

        user = SanitizationUtils.sanitizeUser(user);

        msqUserlRepository.save(user);

        return new MsqlUserResponse(
            user.getUid(),
            user.getUsername(),
            user.getEmail(),
            user.getBiography(),
            user.getProfilePicture(),
            user.getRole().name(),
            user.getCreationDate());
    }

    @Override
    public MsqlUserResponse getUserByUid(String uid) {
        MsqlUser user = msqUserlRepository.findById(uid)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        
        return new MsqlUserResponse(
            user.getUid(),
            user.getUsername(),
            user.getEmail(),
            user.getBiography(),
            user.getProfilePicture(),
            user.getRole().name(),
            user.getCreationDate());
    }   
}
