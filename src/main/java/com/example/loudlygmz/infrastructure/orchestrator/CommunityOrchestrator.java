package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.GameResponse;
import com.example.loudlygmz.domain.service.ICommunityService;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.infrastructure.service.impl.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityOrchestrator {

    private final IMongoUserService mongoUserService;
    private final GameService gameService;
    private final ICommunityService communityService;

    public void joinCommunity(String userId, Integer gameId){
        Instant now = Instant.now();

        GameResponse game = gameService.getGameById(gameId);

        if (game == null) {
            throw new RuntimeException("El juego no existe en la base de datos");
        }

        if(mongoUserService.isUserInCommunity(userId, gameId)){
            throw new RuntimeException("El usuario ya está en la comunidad");
        }

        communityService.addMember(gameId, userId);
        mongoUserService.addJoinedCommunity(userId, gameId, now);
    }
}
