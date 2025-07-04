package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.community.UserCommunityDTO;
import com.example.loudlygmz.application.dto.game.GameDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.enums.CommunityAction;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
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

    public CommunityMembershipResponse joinCommunity(String username, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        UserAndGame object = getUserAndGame(username, gameId);
        
        Optional<String> message = mongoCommunityOrchestrator.joinCommunity(username, gameId);

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.JOIN,
            object.user().getUsername(),
            gameId,
            object.game().getName(),
            Instant.now());
    }

    public CommunityMembershipResponse leaveCommunity(String username, Integer gameId){
        if(gameId <= 0){
            throw new RuntimeException("El ID del juego debe ser mayor a 0");
        }

        UserAndGame object = getUserAndGame(username, gameId);

        Optional<String> message = mongoCommunityOrchestrator.leaveCommunity(username, gameId);

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.LEAVE,
            object.user().getUsername(),
            gameId,
            object.game().getName(),
            Instant.now());
    }

    public List<UserCommunityDTO> getUserLoggedCommunities(String username, int offset, int limit){
        UserResponse userLogged = userOrchestrator.getUserByUsername(username);
        
        List<JoinedCommunity> userCommunities = userLogged.getJoinedCommunities();
        
        // Asegura que offset + limit no excedan
        int end = Math.min(offset + limit, userCommunities.size());
        if (offset > end) return List.of(); // fuera de rango

        List<JoinedCommunity> paginatedCommunities = userCommunities.subList(offset, end);

        List<UserCommunityDTO> response = paginatedCommunities.stream()
        .map(gc -> {
            GameDTO game = gameService.getGameById(gc.gameId());
            return UserCommunityDTO.builder()
            .id(gc.gameId())
            .card(game.getCard())
            .name(game.getName())
            .memberSince(gc.joinedAt())
            .build();
        }).toList();

        return response;
    }
}

