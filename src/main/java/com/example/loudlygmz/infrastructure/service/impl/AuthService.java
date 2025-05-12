package com.example.loudlygmz.infrastructure.service.impl;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.domain.service.IAuthService;
import com.example.loudlygmz.infrastructure.firebase.FirebaseAuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final FirebaseAuthClient firebaseAuthClient;

    @Override
    public void sendPasswordResetEmail(String email) {
        firebaseAuthClient.sendPasswordResetEmail(email);
    }
}
