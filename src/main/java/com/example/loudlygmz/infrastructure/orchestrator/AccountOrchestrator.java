package com.example.loudlygmz.infrastructure.orchestrator;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.auth.FirebaseLoginData;
import com.example.loudlygmz.application.dto.auth.LoginResponse;
import com.example.loudlygmz.application.dto.user.UserLoginRequest;
import com.example.loudlygmz.application.dto.user.UserRegisterRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.application.dto.user.MsqlUserRequest;
import com.example.loudlygmz.infrastructure.firebase.FirebaseAuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountOrchestrator {

    private final FirebaseAuthClient firebaseAuthClient;
    private final UserOrchestrator userOrchestrator;
    

    public UserResponse registerUser(UserRegisterRequest request){
        String uid = firebaseAuthClient.createFirebaseUser(request.getEmail(), request.getPassword());

        MsqlUserRequest msqlUserRequest = new MsqlUserRequest();
        msqlUserRequest.setUid(uid);
        msqlUserRequest.setEmail(request.getEmail());
        msqlUserRequest.setUsername(request.getUsername());
        msqlUserRequest.setBiography(request.getBiography());
        msqlUserRequest.setProfilePicture(request.getProfilePicture());

        try {
            return userOrchestrator.createUser(request, uid);
        } catch (Exception e) {
            firebaseAuthClient.deleteUser(uid);
            throw e;
        }
    }

    public LoginResponse login(UserLoginRequest request){

        FirebaseLoginData firebaseData = firebaseAuthClient.signInWithEmailAndPassword(
            request.getEmail(), request.getPassword());
        
        //Id del Usuario
        String uid = firebaseData.getLocalId();
        //	Un JWT que representa una sesión activa. Es usado para autenticar solicitudes.
        String idToken = firebaseData.getIdToken();
        //Token para renovar la sesión
        String refreshToken = firebaseData.getRefreshToken();
        //Tiempo en el que expira la sesión - En segundos
        Long expiresIn = Long.parseLong(firebaseData.getExpiresIn());
        
        UserResponse user = userOrchestrator.getUserByUid(uid);

        return new LoginResponse(idToken, refreshToken, expiresIn, user);
    }

}
