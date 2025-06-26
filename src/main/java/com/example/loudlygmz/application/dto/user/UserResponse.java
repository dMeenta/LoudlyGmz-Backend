package com.example.loudlygmz.application.dto.user;

import java.time.LocalDateTime;
import java.util.List;

import com.example.loudlygmz.domain.enums.Role;
import com.example.loudlygmz.domain.model.MongoUser.Friend;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String biography;
    private String profilePicture;
    private Role role;
    private List<JoinedCommunity> joinedCommunities;
    private List<String> friendshipRequests;
    private List<String> sentFriendshipRequests;
    private List<Friend> friendsList;
    private List<String> chatsIds;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;
}
