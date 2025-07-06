package com.example.loudlygmz.infrastructure.orchestrator;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.community.UserCommunityDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.enums.CommunityAction;
import com.example.loudlygmz.domain.model.Game;
import com.example.loudlygmz.domain.model.MongoUser.JoinedCommunity;
import com.example.loudlygmz.infrastructure.service.impl.GameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityOrchestrator {
    
    private final MongoCommunityOrchestrator mongoCommunityOrchestrator;
    private final UserOrchestrator userOrchestrator;
    private final GameService gameService;

    private record UserAndGame(UserResponse user, Game game) {}

    private UserAndGame getUserAndGame(String username, String gameName){
        UserResponse user = userOrchestrator.getUserByUsername(username);
        Game game = gameService.getGameByNameWithId(gameName);
        return new UserAndGame(user, game);
    }

    public CommunityMembershipResponse joinCommunity(String username, String gameName){

        UserAndGame object = getUserAndGame(username, gameName);
        
        Optional<String> message = mongoCommunityOrchestrator.joinCommunity(username, object.game.getId());

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.JOIN,
            object.user().getUsername(),
            object.game().getId(),
            object.game().getName(),
            Instant.now());
    }

    public CommunityMembershipResponse leaveCommunity(String username, String gameName){

        UserAndGame object = getUserAndGame(username, gameName);
        
        Optional<String> message = mongoCommunityOrchestrator.leaveCommunity(username, object.game.getId());

        if(message.isPresent()){
            throw new RuntimeException(
                String.format(message.get(), object.user().getUsername(), object.game().getName()));
        }

        return new CommunityMembershipResponse(
            CommunityAction.LEAVE,
            object.user().getUsername(),
            object.game().getId(),
            object.game().getName(),
            Instant.now());
    }

    public Page<UserCommunityDTO> getUserLoggedCommunities(String username, int offset, int limit) {
        UserResponse userLogged = userOrchestrator.getUserByUsername(username);
    
        // Extraer los IDs de comunidades del usuario (ya paginados si es posible)
        List<Integer> gameIds = userLogged.getJoinedCommunities().stream()
            .map(JoinedCommunity::gameId)
            .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(offset / limit, limit);
    
        // Paginar los juegos directamente desde BD
        Page<Game> gamesPage = gameService.getListOfGamesById(gameIds, pageable);
    
        // Mapear a DTO con la fecha de joinedAt
        Map<Integer, Instant> joinedAtMap = userLogged.getJoinedCommunities().stream()
            .collect(Collectors.toMap(JoinedCommunity::gameId, JoinedCommunity::joinedAt));
    
        return gamesPage.map(game -> UserCommunityDTO.builder()
            .name(game.getName())
            .card(game.getAssets().getCard())
            .memberSince(joinedAtMap.get(game.getId()))
            .build());
    }

    public List<String> getCommunityNamesUserLogged(String username) {
        UserResponse userLogged = userOrchestrator.getUserByUsername(username);
    
        // Extraer los IDs de comunidades del usuario (ya paginados si es posible)
        List<Integer> gameIds = userLogged.getJoinedCommunities().stream()
            .map(JoinedCommunity::gameId)
            .collect(Collectors.toList());
        
        return gameService.getListOfGamesById(gameIds).stream()
        .map(c -> c.getName()).toList();
    }

    public boolean checkMembership(String loggedUsername, String gameName){
        Integer gameId = gameService.getGameByNameWithId(gameName).getId();
        return mongoCommunityOrchestrator.checkMembership(loggedUsername, gameId);
    }
}

