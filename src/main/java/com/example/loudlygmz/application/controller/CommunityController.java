package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.community.CommunityMembershipRequest;
import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
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
    public ResponseEntity<ApiResponse<CommunityMembershipResponse>> joinCommunity(@Valid @RequestBody CommunityMembershipRequest request) {
        CommunityMembershipResponse response = communityOrchestrator.joinCommunity(request.getUserId(), request.getGameId());
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                response.getMessage(),
                response));
    }

    @PostMapping("/leave")
    public ResponseEntity<ApiResponse<CommunityMembershipResponse>> leaveCommunity(@RequestBody CommunityMembershipRequest request) {
        CommunityMembershipResponse response = communityOrchestrator.leaveCommunity(request.getUserId(), request.getGameId());
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                response.getMessage(),
                response));
    }

    /*
    
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
