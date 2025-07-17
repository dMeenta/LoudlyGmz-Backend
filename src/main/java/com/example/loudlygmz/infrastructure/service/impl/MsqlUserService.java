package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.exception.DuplicateEmailException;
import com.example.loudlygmz.application.exception.DuplicateUsernameException;
import com.example.loudlygmz.domain.enums.Role;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.repository.IMsqlUserRepository;
import com.example.loudlygmz.domain.service.IMsqlUserService;
import com.example.loudlygmz.infrastructure.common.SanitizationUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MsqlUserService implements IMsqlUserService {

    private final IMsqlUserRepository msqUserlRepository;

    @Override
    public MsqlUser createUser(String uid, RegisterRequestDTO request) {
        if (msqUserlRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("This email is already taken, please use another one.");
        }

        if (msqUserlRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUsernameException("This username is already taken, please use another one.");
        }

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

    @Override
    public Page<MsqlUser> findAllExcludingIds(List<String> excludedIds, Pageable pageable) {
        return msqUserlRepository.findAllExcludingIds(excludedIds, pageable);
    }

    @Override
    public Page<MsqlUser> findUsersByIdIn(List<String> listOfUids, Pageable pageable) {
        return msqUserlRepository.findByUidIn(listOfUids, pageable);
    }    

    @Override
    public Page<MsqlUser> searchUsersByUsernameContaining(String username, Pageable pageable) {
        return msqUserlRepository.findByUsernameContainingIgnoreCase(username, pageable);
    }

    @Override
    public Page<MsqlUser> findUsersByUidInAndUsername(List<String> ids, String username, Pageable pageable){
        return msqUserlRepository.findByUidInAndUsernameContainingIgnoreCase(ids, username, pageable);
    }

}
