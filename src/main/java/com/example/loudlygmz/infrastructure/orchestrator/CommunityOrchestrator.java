package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.GameResponse;
import com.example.loudlygmz.application.dto.JoinCommunityResponse;
import com.example.loudlygmz.application.dto.user.UserResponse;
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

    public JoinCommunityResponse joinCommunity(String userId, Integer gameId){
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
            throw new RuntimeException("El usuario ya está en la comunidad");
        }

        Instant now = Instant.now();

        communityService.addMember(gameId, userId);
        mongoUserService.addJoinedCommunity(userId, gameId, now);

        JoinCommunityResponse response = new JoinCommunityResponse();
        response.setCommunityId(gameId);
        response.setCommunityName(game.getName());
        response.setCommunityCard(game.getCard());
        response.setUsername(userId);
        response.setJoinedAt(now);
        return response;
    }
}
