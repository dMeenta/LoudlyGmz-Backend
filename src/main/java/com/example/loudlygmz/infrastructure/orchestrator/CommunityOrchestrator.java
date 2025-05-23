package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.game.GameDTO;
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

    private record UserAndGame(UserResponse user, GameDTO game) {}

    private UserAndGame getUserAndGame(String username, Integer gameId){
        UserResponse user = userOrchestrator.getUserByUsername(username);
        GameDTO game = gameService.getGameById(gameId);
        return new UserAndGame(user, game);
    }

    public CommunityMembershipResponse joinCommunity(String userId, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        Optional<String> message = mongoCommunityOrchestrator.joinCommunity(userId, gameId);

        UserAndGame object = getUserAndGame(userId, gameId);

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.JOIN,
            userId,
            object.user().getUsername(),
            gameId,
            object.game().getName(),
            Instant.now());
    }

    public CommunityMembershipResponse leaveCommunity(String userId, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        Optional<String> message = mongoCommunityOrchestrator.leaveCommunity(userId, gameId);

        UserAndGame object = getUserAndGame(userId, gameId);

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.LEAVE,
            userId,
            object.user().getUsername(),
            gameId,
            object.game().getName(),
            Instant.now());
    }
}
