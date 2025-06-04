package com.example.loudlygmz.infrastructure.orchestrator;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.domain.service.IMsqlUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserOrchestrator {

    private final IMsqlUserService msqlUserService;
    private final IMongoUserService mongoUserService;

    public UserResponse createUser(RegisterRequestDTO request, String uid){
        
        MsqlUser msqlUser = msqlUserService.createUser(uid, request);
        MongoUser mongoUser = mongoUserService.createUser(uid, request.getUsername());

        return new UserResponse(
            msqlUser.getUsername(),
            msqlUser.getBiography(),
            msqlUser.getProfilePicture(),
            msqlUser.getRole(),
            mongoUser.getJoinedCommunities(),
            mongoUser.getFriendshipRequests(),
            mongoUser.getSentFriendshipRequests(),
            mongoUser.getFriendsList(),
            mongoUser.getChatIds(),
            msqlUser.getCreationDate());
    }

    public UserResponse getUserByUsername(String username){
        
        MsqlUser msqlUser = msqlUserService.getMsqlUserByUsername(username);
        MongoUser mongoUser = mongoUserService.getUserByUsername(username);

        return new UserResponse(
            msqlUser.getUsername(),
            msqlUser.getBiography(),
            msqlUser.getProfilePicture(),
            msqlUser.getRole(),
            mongoUser.getJoinedCommunities(),
            mongoUser.getFriendshipRequests(),
            mongoUser.getSentFriendshipRequests(),
            mongoUser.getFriendsList(),
            mongoUser.getChatIds(),
            msqlUser.getCreationDate());
    }

    public UserResponse getUserByUid(String uid){
        MsqlUser msqlUser = msqlUserService.getMsqlUserByUid(uid);
        MongoUser mongoUser = mongoUserService.getUserByUsername(msqlUser.getUsername());

        return new UserResponse(
            msqlUser.getUsername(),
            msqlUser.getBiography(),
            msqlUser.getProfilePicture(),
            msqlUser.getRole(),
            mongoUser.getJoinedCommunities(),
            mongoUser.getFriendshipRequests(),
            mongoUser.getSentFriendshipRequests(),
            mongoUser.getFriendsList(),
            mongoUser.getChatIds(),
            msqlUser.getCreationDate());
    }
}
