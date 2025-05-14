package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IMongoUserService {
    MongoUser createUser(String userId);
    MongoUser getUser(String userId);
    void addJoinedCommunity(String userId, Integer gameId);
    void removeJoinedCommunity(String userId, Integer gameId);
}
