package com.example.loudlygmz.infrastructure.service.impl;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.auth.FirebaseLoginData;
import com.example.loudlygmz.application.dto.auth.LoginResponse;
import com.example.loudlygmz.application.dto.user.LoginRequestDTO;
import com.example.loudlygmz.domain.service.IAuthService;
import com.example.loudlygmz.infrastructure.firebase.FirebaseAuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final FirebaseAuthClient firebaseAuthClient;
    
    public LoginResponse login(LoginRequestDTO request){

        FirebaseLoginData firebaseData = firebaseAuthClient.signInWithEmailAndPassword(
            request.getEmail(), request.getPassword());
        
        //Id del Usuario
        String uid = firebaseData.getLocalId();
        //Un JWT que representa una sesión activa. Es usado para autenticar solicitudes.
        String idToken = firebaseData.getIdToken();
        //Token para renovar la sesión
        String refreshToken = firebaseData.getRefreshToken();
        //Tiempo en el que expira la sesión - En segundos
        Long expiresIn = Long.parseLong(firebaseData.getExpiresIn());

        return new LoginResponse(uid, idToken, refreshToken, expiresIn);
    }
}
