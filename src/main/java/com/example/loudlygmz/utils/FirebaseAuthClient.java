package com.example.loudlygmz.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URI;
import java.util.Map;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class FirebaseAuthClient {

    @Value("${firebase.api.key}")
    private String firebaseApiKey;

    // Método para iniciar sesión
    public Map<String, String> signInWithEmailAndPassword(String email, String password) throws IOException {
        Map<String, Object> payload = Map.of(
            "email", email,
            "password", password,
            "returnSecureToken", true
        );
        return sendFirebaseAuthRequest("signInWithPassword", payload);
    }

    // Método para registrar un nuevo usuario
    public Map<String, String> signUpWithEmailAndPassword(String email, String password) throws IOException {
        Map<String, Object> payload = Map.of(
            "email", email,
            "password", password,
            "returnSecureToken", true
        );
        return sendFirebaseAuthRequest("signUp", payload);
    }

    // Método para restablecer la contraseña
    public void sendPasswordResetEmail(String email) throws IOException {
        Map<String, Object> payload = Map.of(
            "requestType", "PASSWORD_RESET",
            "email", email
        );
        sendFirebaseAuthRequest("sendOobCode", payload);
    }

    public void revokeUserTokens(String uid) throws FirebaseAuthException{
        FirebaseAuth.getInstance().revokeRefreshTokens(uid);;
    }

    private Map<String, String> sendFirebaseAuthRequest(String endpoint, Map<String, Object> payload) throws IOException {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:" + endpoint + "?key=" + firebaseApiKey;
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(payload);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al autenticar: " + response.body());
            }

            JsonNode responseJson = mapper.readTree(response.body());
            
            // Parsear la respuesta JSON
            String email = responseJson.get("email").asText();
            String localId = responseJson.get("localId").asText();
 
            // Devolver los datos requeridos en un mapa
            return Map.of(
                "email", email,
                "localId", localId
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La solicitud fue interrumpida", e);
        }
    }
}

