package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.user.FriendResponseDTO;
import com.example.loudlygmz.application.dto.user.MinimalUserResponseDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.MsqlUser;
import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.orchestrator.UserOrchestrator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    
    private final UserOrchestrator userOrchestrator;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable @Valid
    @Size(min = 5, max = 30, message = "The length of the username should be between 5 and 30")
    @Pattern(regexp = "^[^\\s]+$", message = "Username must not contain spaces")
    @NotBlank(message = "A username is needed to search into LoudlyGmz")
    String username) {
        UserResponse user = userOrchestrator.getUserByUsername(username);
        return ResponseEntity.ok(
            ApiResponse.success(HttpStatus.OK.value(), "Usuario encontrado", user));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MinimalUserResponseDTO>> getCurrentUser() {
        MsqlUser currentUser = AuthUtils.getCurrentUser();
        MinimalUserResponseDTO response = userOrchestrator.getMinimalInfoOfCurrentUser(currentUser.getUid());

        return ResponseEntity.ok(ApiResponse.success(
            HttpStatus.OK.value(),
            "Perfil del usuario autenticado",
            response));
    }

    @GetMapping("/friends")
    public ResponseEntity<ApiResponse<List<FriendResponseDTO>>> getFriendsList(
        @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit
    ) {
        MsqlUser currentUser = AuthUtils.getCurrentUser();
        List<FriendResponseDTO> friends = userOrchestrator.getFriendsList(currentUser.getUsername(), offset, limit);
    return ResponseEntity.ok(
        ApiResponse.success(HttpStatus.OK.value(),
        "Amigos retornados correctamente",
        friends));
    }

}
