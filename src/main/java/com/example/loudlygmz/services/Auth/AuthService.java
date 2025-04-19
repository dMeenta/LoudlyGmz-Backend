package com.example.loudlygmz.services.Auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.entity.AuthRequests.*;
import com.example.loudlygmz.utils.ApiResponse;
import com.example.loudlygmz.utils.FirebaseAuthClient;

@Service
public class AuthService implements IAuthService {

    private final FirebaseAuthClient firebaseAuthClient;

    public AuthService(FirebaseAuthClient firebaseAuthClient) {
        this.firebaseAuthClient = firebaseAuthClient;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            String response = firebaseAuthClient.signInWithEmailAndPassword(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Autenticación exitosa", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), "Error de Autenticación", e.getMessage())
            );
        }
    }
    
    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        try {
            String response = firebaseAuthClient.signUpWithEmailAndPassword(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.value(), "Usuario Registrado Correctamente", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Error al registrar usuario", e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        try {
            firebaseAuthClient.sendPasswordResetEmail(request.getEmail());;
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Correo de reestablecimiento enviado", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Error al enviar correo de reestablecimiento", e.getMessage())
                );
        }
    }
}
