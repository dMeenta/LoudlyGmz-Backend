package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.game.GameResponse;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.enums.CommunityAction;
import com.example.loudlygmz.domain.service.ICommunityService;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.domain.service.IUserService;
import com.example.loudlygmz.infrastructure.service.impl.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityOrchestrator {

    private final IUserService userService;
    private final IMongoUserService mongoUserService;
    private final GameService gameService;
    private final ICommunityService communityService;

    public CommunityMembershipResponse joinCommunity(String userId, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        GameResponse game = gameService.getGameById(gameId);
        UserResponse user = userService.getUserByUid(userId);

        if (game == null) {
            throw new RuntimeException("El juego no existe en la base de datos");
        }

        if (user == null) {
            throw new RuntimeException("El usuario no existe en la base de datos");
        }

        if(mongoUserService.isUserInCommunity(userId, gameId)){
            throw new RuntimeException(
                String.format("El usuario %s ya es parte de la comunidad de %s", user.getUsername(), game.getName()));
        }

        Instant now = Instant.now();

        communityService.addMember(gameId, userId);
        mongoUserService.addJoinedCommunity(userId, gameId, now);

        return new CommunityMembershipResponse(
            CommunityAction.JOIN, userId, user.getUsername(), gameId, game.getName(), Instant.now()
        );
    }

    public CommunityMembershipResponse leaveCommunity(String userId, Integer gameId){
        boolean isMember = mongoUserService.isUserInCommunity(userId, gameId);

        GameResponse game = gameService.getGameById(gameId);

        if (game == null) {
            throw new RuntimeException("El juego no existe en la base de datos");
        }

        UserResponse user = userService.getUserByUid(userId);

        if (user == null) {
            throw new RuntimeException("El usuario no existe en la base de datos");
        }

        if(!isMember){
            throw new RuntimeException(
                String.format("El usuario %s no es parte de la comunidad de %s", user.getUsername(), game.getName()));
        }
        communityService.removeMember(gameId, userId);
        mongoUserService.removeJoinedCommunity(userId, gameId);

        return new CommunityMembershipResponse(
            CommunityAction.LEAVE, userId, user.getUsername(), gameId, game.getName(), Instant.now()
        );
    }
}
