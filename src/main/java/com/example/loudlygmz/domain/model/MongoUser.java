package com.example.loudlygmz.domain.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "users")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MongoUser {
    @Id
    private String id;

    private String username;
    private List<JoinedCommunity> joinedCommunities;
    private List<String> friendshipRequests;
    private List<String> sentFriendshipRequests;
    private List<Friend> friendsList;
    private List<String> chatIds;

    public record JoinedCommunity(Integer gameId, Instant joinedAt) {}

    public record Friend(String friendUid, Instant friendsSince){}
}
