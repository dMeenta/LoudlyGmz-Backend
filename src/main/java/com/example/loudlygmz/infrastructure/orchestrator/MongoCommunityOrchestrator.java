package com.example.loudlygmz.infrastructure.orchestrator;

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

    public String joinCommunity(String userId, Integer gameId){      

        MongoUser user = mongoUserService.getUser(userId);

        boolean isMember = user.getJoinedCommunities().stream()
            .anyMatch(comm -> comm.gameId().equals(gameId));

        if(isMember == true){
            return "El usuario %s ya es parte de la comunidad de %s";
        }

        communityService.addMember(gameId, userId);
        mongoUserService.addJoinedCommunity(userId, gameId);

        return null;
    }

    public String leaveCommunity(String userId, Integer gameId){
        MongoUser user = mongoUserService.getUser(userId);

        boolean isMember = user.getJoinedCommunities().stream()
            .anyMatch(comm -> comm.gameId().equals(gameId));

        if(isMember == false){
            return "El usuario %s no es parte de la comunidad de %s";
        }

        communityService.removeMember(gameId, userId);
        mongoUserService.removeJoinedCommunity(userId, gameId);
        
        return null;
    }


}
