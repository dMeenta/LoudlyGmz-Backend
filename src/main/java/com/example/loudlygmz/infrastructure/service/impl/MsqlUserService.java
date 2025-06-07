package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.domain.enums.Role;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.repository.IMsqUserRepository;
import com.example.loudlygmz.domain.service.IMsqlUserService;
import com.example.loudlygmz.infrastructure.common.SanitizationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MsqlUserService implements IMsqlUserService {

    private final IMsqUserRepository msqUserlRepository;

    @Override
    public MsqlUser createUser(String uid, RegisterRequestDTO request) {
        MsqlUser user = new MsqlUser();
        user.setUid(uid);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setBiography(request.getBiography());
        user.setProfilePicture(request.getProfilePicture());
        user.setRole(Role.USER);

        user = SanitizationUtils.sanitizeUser(user);

        return msqUserlRepository.save(user);
    }

    @Override
    public MsqlUser getMsqlUserByUsername(String username) {
        return msqUserlRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("El usuario con username '%s' no existe en la base de datos", username)));
    }

    @Override
    public MsqlUser getMsqlUserByUid(String uid) {
        return msqUserlRepository.findById(uid)
        .orElseThrow(()-> new EntityNotFoundException(
            String.format("No se encontró el usuario con el id '%s' en la base de datos", uid)
        ));
    }

    @Override
    public List<MsqlUser> getAllMsqlUserByUid(List<String> listOfUids) {
        return msqUserlRepository.findAllById(listOfUids);
    }
}
