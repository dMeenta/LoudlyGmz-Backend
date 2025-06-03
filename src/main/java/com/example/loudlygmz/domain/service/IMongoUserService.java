package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IMongoUserService {
    MongoUser createUser(String username);
    MongoUser getUserByUsername(String username);

    //Community
    void addJoinedCommunity(String userId, Integer gameId);
    void removeJoinedCommunity(String userId, Integer gameId);
}
