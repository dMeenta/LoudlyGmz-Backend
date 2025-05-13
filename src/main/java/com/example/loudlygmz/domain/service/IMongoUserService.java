package com.example.loudlygmz.domain.service;

import java.time.Instant;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IMongoUserService {
    MongoUser getOrCreateUser(String userId);
    boolean isUserInCommunity(String userId, Integer gameId);
    void addJoinedCommunity(String userId, Integer gameId, Instant joinedAt);
    void removeJoinedCommunity(String userId, Integer gameId);
}
