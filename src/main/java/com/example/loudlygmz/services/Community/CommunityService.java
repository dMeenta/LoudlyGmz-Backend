package com.example.loudlygmz.services.Community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.Community.ICommunityDAO;
import com.example.loudlygmz.entity.CommunityRequests;
import com.example.loudlygmz.utils.ApiResponse;

@Service
public class CommunityService implements ICommunityService {

    @Autowired
    private ICommunityDAO communityDAO;

    @Override
    public ResponseEntity<?> joinCommunity(CommunityRequests request) {
        try {
            String userId = request.getUserId();
            Integer gameId = request.getGameId();
            if (userId == null || gameId == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos", "userId o gameId no pueden ser nulos"));
            }
            communityDAO.joinCommunity(userId, gameId);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Se unió a la comunidad", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Hubo un error al unirse a la comunidad", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> leaveCommunity(CommunityRequests request) {
        try {
            String userId = request.getUserId();
            Integer gameId = request.getGameId();
            if (userId == null || gameId == null) {
                return ResponseEntity.badRequest().body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos", "userId y/o gameId no pueden ser nulos"));
            }
            communityDAO.leaveCommunity(userId, gameId);
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Abandonó la comunidad", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Hubo un error al abandonar a la comunidad", e.getMessage()));
        }
    }
    
}
