package com.example.loudlygmz.infrastructure.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.exception.AlreadyFriendsException;
import com.example.loudlygmz.application.exception.DuplicateFriendshipRequestException;
import com.example.loudlygmz.application.exception.SelfFriendshipException;
import com.example.loudlygmz.application.exception.UserAlreadyExistsException;
import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
import com.example.loudlygmz.domain.repository.IMongoUserRepository;
import com.example.loudlygmz.domain.service.IFriendsService;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.mongodb.DuplicateKeyException;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MongoUserService implements IMongoUserService, IFriendsService{

    private final IMongoUserRepository mongoUserRepository;

    @Override
    public MongoUser getUserByUsername(String username) {
        return mongoUserRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("El usuario con username '%s' no existe en la base de datos", username)));
    }

    @Override
    public MongoUser createUser(String uid, String username){
        MongoUser user = new MongoUser();
        user.setId(uid);
        user.setUsername(username);
        user.setJoinedCommunities(new ArrayList<>());
        user.setFriendshipRequests(new ArrayList<>());
        user.setSentFriendshipRequests(new ArrayList<>());
        user.setFriendsList(new ArrayList<>());
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

    @Override
    public void sendFriendshipRequest(String requesterUsername, String targetUsername) {
        MongoUser sender = getUserByUsername(requesterUsername);
        MongoUser receiver = getUserByUsername(targetUsername);

        validateFriendshipRequest(sender, receiver);

        addSentFriendshipRequest(sender, receiver.getId());
        addReceivedFriendshipRequest(receiver, sender.getId());
    }

    @Override
    public void validateFriendshipRequest(MongoUser sender, MongoUser receiver) {
        if (sender.getId().equals(receiver.getId())) {
            throw new SelfFriendshipException();
        }
        if (sender.getSentFriendshipRequests().contains(receiver.getId())) {
            throw new DuplicateFriendshipRequestException();
        }
        if (sender.getFriendsList().stream()
        .anyMatch(f -> f.friendUid().equals(receiver.getId()))) {
            throw new AlreadyFriendsException();
        }
    }

    @Override
    public void addReceivedFriendshipRequest(MongoUser receiver, String senderId) {
        receiver.getFriendshipRequests().add(senderId);
        mongoUserRepository.save(receiver);
    }
    @Override
    public void removeReceivedFriendshipRequest(MongoUser receiver, String senderId) {
        receiver.getFriendshipRequests().remove(senderId);
        mongoUserRepository.save(receiver);
    }
    
    @Override
    public void addSentFriendshipRequest(MongoUser sender, String receiverId) {
        sender.getSentFriendshipRequests().add(receiverId);
        mongoUserRepository.save(sender);
    }

    @Override
    public void removeSentFriendRequest(MongoUser sender, String receiverId) {
        sender.getSentFriendshipRequests().remove(receiverId);
        mongoUserRepository.save(sender);        
    }

    @Override
    public void rejectFriendshipRequest(String rejecterUsername, String rejectedUsername) {
        MongoUser rejecter = getUserByUsername(rejecterUsername);
        MongoUser rejected = getUserByUsername(rejectedUsername);

        removeReceivedFriendshipRequest(rejecter, rejected.getId());
        removeSentFriendRequest(rejected, rejecter.getId());
    }

    @Override
    public void acceptFriendshipRequest(String accepterUsername, String acceptedUsername) {
        MongoUser accepter = getUserByUsername(accepterUsername);
        MongoUser accepted = getUserByUsername(acceptedUsername);

        accepter.getFriendsList().add(new MongoUser.Friend(accepted.getId(), Instant.now()));
        accepted.getFriendsList().add(new MongoUser.Friend(accepter.getId(), Instant.now()));

        removeReceivedFriendshipRequest(accepter, accepted.getId());
        removeSentFriendRequest(accepted, accepter.getId());
    }

    @Override
    public void cancelFriendshipRequest(String cancellerUsername, String cancelledUsername) {
        MongoUser canceller = getUserByUsername(cancellerUsername);
        MongoUser cancelled = getUserByUsername(cancelledUsername);

        removeReceivedFriendshipRequest(cancelled, canceller.getId());
        removeSentFriendRequest(canceller, cancelled.getId());
    }
}
