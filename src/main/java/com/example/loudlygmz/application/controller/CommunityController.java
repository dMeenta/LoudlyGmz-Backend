package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.JoinCommunityRequest;
import com.example.loudlygmz.application.dto.JoinCommunityResponse;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.orchestrator.CommunityOrchestrator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    
    private final CommunityOrchestrator communityOrchestrator;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<JoinCommunityResponse>> joinCommunity(@Valid @RequestBody JoinCommunityRequest request) {
        JoinCommunityResponse response = communityOrchestrator.joinCommunity(request.getUserId(), request.getGameId());
        
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Usuario ingreso a la comunidad",
                response));
    }
    /* 

    @PostMapping("/leave")
    public ResponseEntity<?> leaveCommunity(@RequestBody CommunityRequests request) {
        return communityService.leaveCommunity(request);
    }

    @GetMapping("/isMember")
    public ResponseEntity<?> checkMembership(@RequestParam String userId, @RequestParam Integer gameId) {
        return communityService.checkMembership(userId, gameId);
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<?> getCommunitiesByUser(@PathVariable String uid) {
        return communityService.getCommunitiesByUser(uid);
    }
     */
}
