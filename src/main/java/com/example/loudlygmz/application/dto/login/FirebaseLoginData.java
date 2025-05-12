package com.example.loudlygmz.application.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseLoginData {
    private String localId;
    private String idToken;
    private String refreshToken;
    private String expiresIn;
}
