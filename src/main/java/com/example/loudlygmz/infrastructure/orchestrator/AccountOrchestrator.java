package com.example.loudlygmz.infrastructure.orchestrator;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.application.exception.DuplicateEmailException;
import com.example.loudlygmz.application.exception.DuplicateUsernameException;
import com.example.loudlygmz.infrastructure.firebase.FirebaseAuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountOrchestrator {

    private final FirebaseAuthClient firebaseAuthClient;
    private final UserOrchestrator userOrchestrator;

    public UserResponse registerUser(RegisterRequestDTO request){
        String uid = firebaseAuthClient.createFirebaseUser(request.getEmail(), request.getPassword());

        try {
            return userOrchestrator.createUser(request, uid);
        } catch (DuplicateEmailException e) {
            firebaseAuthClient.deleteUser(uid);
            throw e;
        } catch (DuplicateUsernameException e) {
            firebaseAuthClient.deleteUser(uid);
            throw e;
        } catch (Exception e) {
            firebaseAuthClient.deleteUser(uid);
            throw e;
        }
    }
}
