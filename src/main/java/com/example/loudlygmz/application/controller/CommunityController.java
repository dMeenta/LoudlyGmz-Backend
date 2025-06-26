package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.community.CommunityMembershipRequest;
import com.example.loudlygmz.application.dto.community.CommunityMembershipResponse;
import com.example.loudlygmz.application.dto.community.UserCommunityDTO;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;
import com.example.loudlygmz.infrastructure.orchestrator.CommunityOrchestrator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(
    name = "Community Controller",
    description = "Controller to manage community games actions"
)
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    
    private final CommunityOrchestrator communityOrchestrator;

    @Operation(
        summary = "Join a community endpoint",
        description = "Endpoint for users to join a community"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO<CommunityMembershipResponse>> joinCommunity(@Valid @RequestBody CommunityMembershipRequest request) {
        CommunityMembershipResponse response = communityOrchestrator.joinCommunity(request.getUserId(), request.getGameId());
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                response.getMessage(),
                response));
    }

    @Operation(
        summary = "Leave a community endpoint",
        description = "Endpoint for users to leave a community"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @PostMapping("/leave")
    public ResponseEntity<ResponseDTO<CommunityMembershipResponse>> leaveCommunity(@RequestBody CommunityMembershipRequest request) {
        CommunityMembershipResponse response = communityOrchestrator.leaveCommunity(request.getUserId(), request.getGameId());
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                response.getMessage(),
                response));
    }

    @GetMapping("/userLogged")
    public ResponseEntity<ResponseDTO<List<UserCommunityDTO>>> getUserGameCommunities(
        @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue="10") int limit
    ) {
        MsqlUser currentUser = AuthUtils.getCurrentUser();
        List<UserCommunityDTO> response = communityOrchestrator.getUserLoggedCommunities(currentUser.getUsername(), offset, limit);
        return ResponseEntity.ok(
            ResponseDTO.success(
                HttpStatus.OK.value(),
                "Comunidades del usuario listadas",
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
