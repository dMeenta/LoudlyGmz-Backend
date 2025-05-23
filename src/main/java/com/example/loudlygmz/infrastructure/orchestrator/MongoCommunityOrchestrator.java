package com.example.loudlygmz.infrastructure.orchestrator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.domain.model.MongoUser;
import com.example.loudlygmz.domain.service.ICommunityService;
import com.example.loudlygmz.domain.service.IMongoUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MongoCommunityOrchestrator {
    private final IMongoUserService mongoUserService;
    private final ICommunityService communityService;

    public Optional<String> joinCommunity(String username, Integer gameId){      

        MongoUser user = mongoUserService.getUserByUsername(username);

        boolean isMember = user.getJoinedCommunities().stream()
            .anyMatch(comm -> comm.gameId().equals(gameId));

        if(isMember){
            return Optional.of("El usuario %s ya es parte de la comunidad de %s");
        }

        communityService.addMember(gameId, username);
        mongoUserService.addJoinedCommunity(username, gameId);

        return Optional.empty();
    }

    public Optional<String> leaveCommunity(String username, Integer gameId){
        MongoUser user = mongoUserService.getUserByUsername(username);

        boolean isMember = user.getJoinedCommunities().stream()
            .anyMatch(comm -> comm.gameId().equals(gameId));

        if(!isMember){
            return Optional.of("El usuario %s no es parte de la comunidad de %s");
        }

        communityService.removeMember(gameId, username);
        mongoUserService.removeJoinedCommunity(username, gameId);
        
        return Optional.empty();
    }


}
