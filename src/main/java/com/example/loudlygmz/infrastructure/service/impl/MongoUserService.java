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
    public MongoUser getUser(String userId) {
        return mongoUserRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException(
            "Este usuario no existe en la base de datos"));
    }

    @Override
    public MongoUser createUser(String userId){
        MongoUser user = new MongoUser();
        user.setId(userId);
        user.setJoinedCommunities(new ArrayList<>());
        user.setFriendIds(new ArrayList<>());
        user.setChatIds(new ArrayList<>());
        return mongoUserRepository.save(user);
    }
    
    @Override
    public void addJoinedCommunity(String userId, Integer gameId) {
        Instant now = Instant.now();
        MongoUser user = getUser(userId);
        List<JoinedCommunity> joinedCommunities = user.getJoinedCommunities();
        joinedCommunities.add(new MongoUser.JoinedCommunity(gameId, now));
        mongoUserRepository.save(user);
    }

    @Override
    public void removeJoinedCommunity(String userId, Integer gameId) {
        MongoUser user = getUser(userId);
        user.getJoinedCommunities().removeIf(c -> c.gameId().equals(gameId));
        mongoUserRepository.save(user);
    }
    
}
