package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.domain.service.IFriendsService;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
@Validated
public class SocialController {

  private final IFriendsService friendsService;

  @PostMapping("/friend-request/send")
  public ResponseEntity<ResponseDTO<String>> sendFriendshipRequest(@Valid @RequestBody String receiverUsername) {
    String userLogged = AuthUtils.getCurrentUser().getUsername();
    friendsService.sendFriendshipRequest(userLogged, receiverUsername);
    return ResponseEntity.ok(
      ResponseDTO.success(HttpStatus.OK.value(),
      "Friendship request sent.",
      String.format("'%s' sent a friend request to '%s'.", userLogged, receiverUsername)));
  }

  @PostMapping("/friend-request/accept")
  public ResponseEntity<ResponseDTO<String>> acceptFriendshipRequest(@Valid String acceptedUsername) {
    String userLogged = AuthUtils.getCurrentUser().getUsername();
    friendsService.acceptFriendshipRequest(userLogged, acceptedUsername);
    return ResponseEntity.ok(
      ResponseDTO.success(HttpStatus.OK.value(),
      "Friendship request accepted.",
      String.format("'%s' accepted the friend request of '%s'.", userLogged, acceptedUsername)));
  }

  @PostMapping("/friend-request/reject")
  public ResponseEntity<ResponseDTO<String>> rejectFriendshipRequest(@Valid @RequestBody String rejectedUsername) {
    String userLogged = AuthUtils.getCurrentUser().getUsername();
    friendsService.rejectFriendshipRequest(userLogged, rejectedUsername);
    return ResponseEntity.ok(
      ResponseDTO.success(HttpStatus.OK.value(),
      "Friendship request rejected.",
      String.format("'%s' rejected the friend request of '%s'.", userLogged, rejectedUsername)));
  }
}
