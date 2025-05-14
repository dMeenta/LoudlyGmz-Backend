package com.example.loudlygmz.infrastructure.orchestrator;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.MsqlUserRequest;
import com.example.loudlygmz.application.dto.user.MsqlUserResponse;
import com.example.loudlygmz.application.dto.user.UserRegisterRequest;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.enums.Role;
import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.domain.service.IMsqlUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserOrchestrator {

    private final IMsqlUserService msqlUserService;
    private final IMongoUserService mongoUserService;

    public UserResponse createUser(UserRegisterRequest request, String uid){
        
        MsqlUserRequest msqlUserRequest = new MsqlUserRequest();
        msqlUserRequest.setUid(uid);
        msqlUserRequest.setEmail(request.getEmail());
        msqlUserRequest.setUsername(request.getUsername());
        msqlUserRequest.setBiography(request.getBiography());
        msqlUserRequest.setProfilePicture(request.getProfilePicture());
        
        MongoUser mongoUser = mongoUserService.createUser(uid);
        MsqlUserResponse msqlUser = msqlUserService.createUser(msqlUserRequest);

        return new UserResponse(
            msqlUser.getUid(),
            msqlUser.getUsername(),
            msqlUser.getEmail(),
            msqlUser.getBiography(),
            msqlUser.getProfilePicture(),
            Role.valueOf(msqlUser.getRole()),
            mongoUser.getJoinedCommunities(),
            mongoUser.getFriendIds(),
            mongoUser.getChatIds(),
            msqlUser.getCreationDate());
    }

    public UserResponse getUserByUid(String uid){
        
        MsqlUserResponse msqlUser = msqlUserService.getUserByUid(uid);

        if(msqlUser==null){
            throw new RuntimeException("El usuario no existe en la base de datos");
        }
        
        MongoUser mongoUser = mongoUserService.getUser(uid);

        return new UserResponse(
            msqlUser.getUid(),
            msqlUser.getUsername(),
            msqlUser.getEmail(),
            msqlUser.getBiography(),
            msqlUser.getProfilePicture(),
            Role.valueOf(msqlUser.getRole()),
            mongoUser.getJoinedCommunities(),
            mongoUser.getFriendIds(),
            mongoUser.getChatIds(),
            msqlUser.getCreationDate());
    }
}
