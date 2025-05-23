package com.example.loudlygmz.infrastructure.service.impl;

import java.time.Instant;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.domain.model.Community;

import com.example.loudlygmz.domain.repository.ICommunityRepository;
import com.example.loudlygmz.domain.service.ICommunityService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService implements ICommunityService {
    
    private final ICommunityRepository communityRepository;

    @Override
    public Community createCommunity(Integer gameId) {
        Community community = new Community();
        community.setGameId(gameId);
        community.setMembers(new ArrayList<>());
        return communityRepository.save(community);
    }

    @Override
    public Community addMember(Integer gameId, String userId) {
        Community community = communityRepository.findById(gameId)
        .orElseGet(() -> {
            Community newCommunity = new Community();
            newCommunity.setGameId(gameId);
            newCommunity.setMembers(new ArrayList<>());
            return newCommunity;});

        community.getMembers().add(new Community.Member(userId, Instant.now()));
        return communityRepository.save(community);
    }

    @Override
    public void removeMember(Integer gameId, String userId) {
        Community community = communityRepository.findById(gameId)
        .orElseThrow(()-> new EntityNotFoundException(
            String.format("La comunidad con ID: %s  comunidad no existe", gameId)));
        community.getMembers().removeIf(member -> member.userId().equals(userId));
        communityRepository.save(community);
    }    

   /*    
    @Override
    public ResponseEntity<?> checkMembership(String userId, Integer gameId){
        try {
            if (userId == null || gameId == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos", "userId y/o gameId no pueden ser nulos"));
            }
            boolean response = communityDAO.checkMembership(userId, gameId);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Verificación de miembro de la comunidad", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Hubo un error al verificar el miembro de la comunidad", e.getMessage()));
        }
    }

    @Override
    public UserCommunitiesResponse getCommunitiesByUserId(String userId) {


        try {
            if (userId == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos", "userId no puede ser nulo"));
            }
            List<?> response = communityDAO.getCommunitiesByUser(userId);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Lista de comunidades del usuario recuperada", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Hubo un error al recuperar la lista de comunidades del usuario", e.getMessage()));
        }
    } */

    
}
