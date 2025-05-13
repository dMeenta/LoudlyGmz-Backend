package com.example.loudlygmz.infrastructure.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
import com.example.loudlygmz.domain.repository.IMongoUserRepository;
import com.example.loudlygmz.domain.service.IMongoUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MongoUserService implements IMongoUserService{

    private final IMongoUserRepository mongoUserRepository;

    @Override
    public MongoUser getOrCreateUser(String userId) {
        return mongoUserRepository.findById(userId)
        .orElseGet(() -> {
            MongoUser user = new MongoUser();
            user.setId(userId);
            user.setJoinedCommunities(new ArrayList<>());
            user.setFriendIds(new ArrayList<>());
            user.setChatIds(new ArrayList<>());
            return user;
        });
    }
    
    @Override
    public void addJoinedCommunity(String userId, Integer gameId, Instant joinedAt) {
        MongoUser user = getOrCreateUser(userId);
        List<JoinedCommunity> joinedCommunities = user.getJoinedCommunities();
        boolean alreadyJoined = joinedCommunities.stream()
            .anyMatch(c -> c.gameId().equals(gameId));
        if (!alreadyJoined) {
            joinedCommunities.add(new MongoUser.JoinedCommunity(gameId, joinedAt));
            mongoUserRepository.save(user);
        }    
    }

    @Override
    public boolean isUserInCommunity(String userId, Integer gameId) {
        return mongoUserRepository.findById(userId)
        .map(user -> user.getJoinedCommunities().stream()
        .anyMatch(c -> c.gameId().equals(gameId)))
        .orElse(false);
    }
    
}
