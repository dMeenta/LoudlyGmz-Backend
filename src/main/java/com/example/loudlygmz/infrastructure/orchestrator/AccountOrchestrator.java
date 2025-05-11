package com.example.loudlygmz.infrastructure.orchestrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.UserRegisterRequest;
import com.example.loudlygmz.application.dto.user.UserRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.service.IUserService;
import com.example.loudlygmz.infrastructure.common.FirebaseAuthClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountOrchestrator {

    @Autowired
    private FirebaseAuthClient firebaseAuthClient;
    @Autowired
    private IUserService userService;

    public UserResponse registerUser(UserRegisterRequest request){
        
        String uid = firebaseAuthClient.createFirebaseUser(request.getEmail(), request.getPassword());

        UserRequest userRequest = new UserRequest();

        userRequest.setUid(uid);
        userRequest.setEmail(request.getEmail());
        userRequest.setUsername(request.getUsername());
        userRequest.setBiography(request.getBiography());
        userRequest.setProfilePicture(request.getProfilePicture());

        try {
            return userService.createUser(userRequest);
        } catch (Exception e) {
            firebaseAuthClient.deleteUser(uid);
            throw e;
        }
    }

}
