package com.example.loudlygmz.infrastructure.orchestrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.FriendResponseDTO;
import com.example.loudlygmz.application.dto.user.MinimalUserResponseDTO;
import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.application.dto.user.UserWithRelationshipDTO;
import com.example.loudlygmz.domain.enums.FriendshipStatus;
import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.domain.model.MongoUser.Friend;
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

    public Page<UserWithRelationshipDTO> searchUsersByUsername(String userLogged, String usernameQuery, int offset, int limit) {
        MongoUser currentUser = mongoUserService.getUserByUsername(userLogged);
        Pageable pageable = PageRequest.of(offset / limit, limit);
    
        Page<MsqlUser> usersPage = msqlUserService.searchUsersByUsernameContaining(usernameQuery, pageable);
    
        return usersPage.map(user ->
            new UserWithRelationshipDTO(
                user.getUsername(),
                user.getProfilePicture(),
                getRelationshipStatus(user.getUid(), currentUser)));
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

    public MinimalUserResponseDTO getMinimalInfoOfCurrentUser(String uid){
        MsqlUser msqlUser = msqlUserService.getMsqlUserByUid(uid);

        return new MinimalUserResponseDTO(
            msqlUser.getUsername(),
            msqlUser.getProfilePicture(),
            msqlUser.getRole());
    }

    public Page<FriendResponseDTO> getUserFriendsList(String usernameLogged, int offset, int limit) {
        MongoUser userLogged = mongoUserService.getUserByUsername(usernameLogged);

        List<String> userIds = userLogged.getFriendsList()
            .stream().map(f -> f.friendUid()).toList();

        Pageable pageable = PageRequest.of(offset / limit, limit);

        Page<MsqlUser> usersPage = msqlUserService.findUsersByIdIn(userIds, pageable);

        List<FriendResponseDTO> friends = usersPage.getContent().stream()
            .map(user -> new FriendResponseDTO(user.getUsername(), user.getProfilePicture()))
            .toList();

        return new PageImpl<>(friends, pageable, usersPage.getTotalElements());
    }

    public Page<UserWithRelationshipDTO> getUserFriendsList(String usernameLogged, String usernameTarget, int offset, int limit) {
        MongoUser userTarget = mongoUserService.getUserByUsername(usernameTarget);
        MongoUser userLogged = mongoUserService.getUserByUsername(usernameLogged);

        List<String> userIds = userTarget.getFriendsList()
            .stream().map(f -> f.friendUid()).toList();

        Pageable pageable = PageRequest.of(offset / limit, limit);

        Page<MsqlUser> usersPage = msqlUserService.findUsersByIdIn(userIds, pageable);

        return usersPage.map(user->{
            String targetUserId = user.getUid();
            return new UserWithRelationshipDTO(
                user.getUsername(),
                user.getProfilePicture(),
                getRelationshipStatus(targetUserId, userLogged));
        });
    }

    public Page<UserWithRelationshipDTO> getUsersExcludingCurrentAndFriends(String usernameLogged, int offset, int limit) {
        MongoUser userLogged = mongoUserService.getUserByUsername(usernameLogged);

        List<String> friendUids = userLogged.getFriendsList().stream()
            .map(MongoUser.Friend::friendUid)
            .toList();

        List<String> excludedIds = new ArrayList<>();
        excludedIds.add(userLogged.getId()); // uid del loggeado
        excludedIds.addAll(friendUids);

        Pageable pageable = PageRequest.of(offset, limit);

        Page<MsqlUser> usersPage = msqlUserService.findAllExcludingIds(excludedIds, pageable);

        return usersPage.map(user -> {
            String targetUserId = user.getUid();
            return new UserWithRelationshipDTO(
                user.getUsername(),
                user.getProfilePicture(),
                getRelationshipStatus(targetUserId, userLogged));
            });
    }

    private FriendshipStatus getRelationshipStatus(String targetUserId, MongoUser currentUser){
        List<String> sentRequests = currentUser.getSentFriendshipRequests();
        List<String> receivedRequests = currentUser.getFriendshipRequests();
        List<String> friendUids = currentUser.getFriendsList().stream().map(Friend::friendUid).toList();

        FriendshipStatus status;
        if (sentRequests.contains(targetUserId)) {
            status = FriendshipStatus.PENDING_SENT;
        } else if (receivedRequests.contains(targetUserId)) {
            status = FriendshipStatus.PENDING_RECEIVED;
        } else if (friendUids.contains(targetUserId) || targetUserId.equals(currentUser.getId())){
            status = FriendshipStatus.FRIENDS;
        } else {
            status = FriendshipStatus.NONE;
        }
        return status;
    }

    public Page<UserWithRelationshipDTO> getFriendRequests(String usernameLogged, int offset, int limit){
        MongoUser userLogged = mongoUserService.getUserByUsername(usernameLogged);

        List<String> userIds = userLogged.getFriendshipRequests();
        Pageable pageable = PageRequest.of(offset / limit, limit);

        Page<MsqlUser> usersPage = msqlUserService.findUsersByIdIn(userIds, pageable);

        List<UserWithRelationshipDTO> users = usersPage.getContent().stream()
        .map(user -> new UserWithRelationshipDTO(user.getUsername(), user.getProfilePicture(), FriendshipStatus.PENDING_RECEIVED))
        .toList();

        return new PageImpl<>(users, pageable, usersPage.getTotalElements());
    }
}
