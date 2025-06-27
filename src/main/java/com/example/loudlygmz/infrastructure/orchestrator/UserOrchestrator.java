package com.example.loudlygmz.infrastructure.orchestrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.user.FriendResponseDTO;
import com.example.loudlygmz.application.dto.user.MinimalUserResponseDTO;
import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
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

    public List<FriendResponseDTO> getUserFriendsList(String loggedUsername, int offset, int limit) {
        MongoUser userLogged = mongoUserService.getUserByUsername(loggedUsername);

        List<Friend> allFriends = userLogged.getFriendsList();
        
        // Asegura que offset + limit no excedan
        int end = Math.min(offset + limit, allFriends.size());
        if (offset > end) return List.of(); // fuera de rango

        List<Friend> paginated = allFriends.subList(offset, end);

        List<String> friendUids = paginated.stream()
            .map(Friend::friendUid)
            .toList();

        Map<String, MsqlUser> userMap = msqlUserService.getAllMsqlUserByUid(friendUids).stream()
            .collect(Collectors.toMap(MsqlUser::getUid, Function.identity()));

        return paginated.stream().map(f -> {
            MsqlUser u = userMap.get(f.friendUid());
            return new FriendResponseDTO(
                f.friendUid(),
                u.getUsername(),
                u.getProfilePicture()
            );}).toList();
    }

    public Page<MinimalUserResponseDTO> getUsersExcludingCurrentAndFriends(String username, int offset, int limit) {
        MongoUser mongoUser = mongoUserService.getUserByUsername(username); // Faltaba ';'

        List<String> friendUids = mongoUser.getFriendsList().stream()
            .map(MongoUser.Friend::friendUid)
            .toList();

        List<String> excludedIds = new ArrayList<>();
        excludedIds.add(mongoUser.getId()); // uid del loggeado
        excludedIds.addAll(friendUids);

        Pageable pageable = PageRequest.of(offset, limit);

        Page<MsqlUser> usersPage = msqlUserService.findAllExcludingIds(excludedIds, pageable);

        return usersPage.map(user -> new MinimalUserResponseDTO(
            user.getUsername(),
            user.getProfilePicture(),
            user.getRole()));
    }
}
