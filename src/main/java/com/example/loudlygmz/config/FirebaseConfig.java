package com.example.loudlygmz.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {

        InputStream refreshToken = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");

        if(refreshToken == null){
            throw new IOException("El archivo serviceAccountKey.json no se encontró en los recursos.");
        }

        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).build();
        
        FirebaseApp.initializeApp(options);
    }

    @Bean
    Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}
