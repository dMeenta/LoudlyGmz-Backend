package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.game.GameResponse;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.enums.CommunityAction;
import com.example.loudlygmz.infrastructure.service.impl.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityOrchestrator {
    
    private final MongoCommunityOrchestrator mongoCommunityOrchestrator;
    private final UserOrchestrator userOrchestrator;
    private final GameService gameService;

    private record UserAndGame(UserResponse user, GameResponse game) {}

    private UserAndGame getUserAndGame(String userId, Integer gameId){
        UserResponse user = userOrchestrator.getUserByUid(userId);
        GameResponse game = gameService.getGameById(gameId);
        return new UserAndGame(user, game);
    }

    public CommunityMembershipResponse joinCommunity(String userId, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        String message = mongoCommunityOrchestrator.joinCommunity(userId, gameId);

        UserAndGame object = getUserAndGame(userId, gameId);

        if(message != null){
            throw new RuntimeException(
                String.format(message, object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.JOIN,
            object.user.getUid(),
            object.user.getUsername(),
            object.game.getId(),
            object.game.getName(),
            Instant.now());
    }

    public CommunityMembershipResponse leaveCommunity(String userId, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        String message = mongoCommunityOrchestrator.leaveCommunity(userId, gameId);

        UserAndGame object = getUserAndGame(userId, gameId);

        if(message != null){
            throw new RuntimeException(
                String.format(message, object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.LEAVE,
            object.user.getUid(),
            object.user.getUsername(),
            object.game.getId(),
            object.game.getName(),
            Instant.now());
    }
}
