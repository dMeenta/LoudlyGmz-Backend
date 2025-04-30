package com.example.loudlygmz.services.Auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.entity.AuthRequests.*;
import com.example.loudlygmz.utils.ApiResponse;
import com.example.loudlygmz.utils.FirebaseAuthClient;

@Service
public class AuthService implements IAuthService {

    @Autowired
    FirebaseAuthClient firebaseAuthClient;

    public AuthService(FirebaseAuthClient firebaseAuthClient) {
        this.firebaseAuthClient = firebaseAuthClient;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            Map<String, String> response = firebaseAuthClient.signInWithEmailAndPassword(request.getEmail(), request.getPassword());
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
            Map<String, String> response = firebaseAuthClient.signUpWithEmailAndPassword(request.getEmail(), request.getPassword());
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

    @Override
    public ResponseEntity<?> logout(String uid) {
        try {
            firebaseAuthClient.revokeUserTokens(uid);
            return ResponseEntity.ok(
                ApiResponse.success(HttpStatus.OK.value(), "Sesión cerrada exitosamente", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al cerrar sesión", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(String uid) {
        try {
            firebaseAuthClient.deleteUser(uid);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Usuario eliminado de firebase correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", e.getMessage()));
        }
    }
}
