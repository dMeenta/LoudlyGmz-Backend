package com.example.loudlygmz.infrastructure.firebase;

import com.example.loudlygmz.application.dto.FirebaseLoginData;
import com.example.loudlygmz.application.exception.InvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FirebaseAuthClient {

    @Value("${firebase.api.key}")
    private String firebaseApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Método para iniciar sesión
    public FirebaseLoginData signInWithEmailAndPassword(String email, String password){
        Map<String, Object> payload = Map.of(
            "email", email,
            "password", password,
            "returnSecureToken", true);
        try {
            return sendFirebaseAuthRequest(
                "signInWithPassword",
                payload,
                FirebaseLoginData.class);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("INVALID_LOGIN_CREDENTIALS")) {
                throw new InvalidCredentialsException("Correo o contraseña incorrectos.");
            }
            throw e;
        }
    }

    // Método para registrar un nuevo usuario
    public String createFirebaseUser(String email, String password) {
        UserRecord.CreateRequest firebaseRequest = new UserRecord.CreateRequest()
        .setEmail(email)
        .setPassword(password);

        try {
            UserRecord record = FirebaseAuth.getInstance().createUser(firebaseRequest);
            return record.getUid();
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error creando usuario en Firebase: " + e.getMessage());
        }
    }

    // Método para restablecer la contraseña
    public void sendPasswordResetEmail(String email) {
        Map<String, Object> payload = Map.of(
            "requestType", "PASSWORD_RESET",
            "email", email
        );

        try {
            sendFirebaseAuthRequest("sendOobCode", payload, Object.class);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("EMAIL_NOT_FOUND")) {
                throw new RuntimeException("El correo no está registrado en Firebase.");
            }
            throw e;
        }
}

    public void deleteUser(String uid){
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error eliminando usuario en Firebase: " + e.getMessage(), e);
        }
    }

    private <T> T sendFirebaseAuthRequest(String endpoint, Map<String, Object> payload, Class<T> responseType) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:" + endpoint + "?key=" + firebaseApiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<T> response = restTemplate.postForEntity(url, request, responseType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al llamar a Firebase: " + e.getResponseBodyAsString(), e);
        }
    }
}

