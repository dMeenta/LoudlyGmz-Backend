package com.example.loudlygmz.infrastructure.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.exception.UserAlreadyExistsException;
import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
import com.example.loudlygmz.domain.repository.IMongoUserRepository;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.mongodb.DuplicateKeyException;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MongoUserService implements IMongoUserService{

    private final IMongoUserRepository mongoUserRepository;

    @Override
    public MongoUser getUserByUsername(String username) {
        return mongoUserRepository.findById(username)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("El usuario con username '%s' no existe en la base de datos", username)));
    }

    @Override
    public MongoUser createUser(String username){
        MongoUser user = new MongoUser();
        user.setId(username);
        user.setJoinedCommunities(new ArrayList<>());
        user.setFriendIds(new ArrayList<>());
        user.setChatIds(new ArrayList<>());
        try {
            return mongoUserRepository.insert(user);
        } catch (DuplicateKeyException ex) {
            throw new UserAlreadyExistsException(username);
        }
    }
    
    @Override
    public void addJoinedCommunity(String username, Integer gameId) {
        Instant now = Instant.now();
        MongoUser user = getUserByUsername(username);
        List<JoinedCommunity> joinedCommunities = user.getJoinedCommunities();
        joinedCommunities.add(new MongoUser.JoinedCommunity(gameId, now));
        mongoUserRepository.save(user);
    }

    @Override
    public void removeJoinedCommunity(String username, Integer gameId) {
        MongoUser user = getUserByUsername(username);
        user.getJoinedCommunities().removeIf(c -> c.gameId().equals(gameId));
        mongoUserRepository.save(user);
    }
    
}
