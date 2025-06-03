package com.example.loudlygmz.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.AcceptFriendshipRequestDTO;
import com.example.loudlygmz.application.dto.RejectFriendshipRequestDTO;
import com.example.loudlygmz.application.dto.SendFriendshipRequestDTO;
import com.example.loudlygmz.domain.service.IFriendsService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
@Validated
public class SocialController {

  private final IFriendsService friendsService;

  @PostMapping("/friend-request/send")
  public ResponseEntity<ApiResponse<?>> sendFriendshipRequest(@Valid @RequestBody SendFriendshipRequestDTO request) {
    String sender = request.getSenderUsername();
    String receiver = request.getReceiverUsername();
    friendsService.sendFriendshipRequest(sender, receiver);
    return ResponseEntity.ok(
      ApiResponse.success(HttpStatus.OK.value(),
      "Friendship request sent.",
      String.format("'%s' sent a friend request to '%s'.", sender, receiver)));
  }

  @PostMapping("/friend-request/accept")
  public ResponseEntity<ApiResponse<?>> acceptFriendshipRequest(@Valid @RequestBody AcceptFriendshipRequestDTO request) {
    String accepter = request.getAccepterUsername();
    String accepted = request.getAcceptedUsername();
    friendsService.acceptFriendshipRequest(accepter, accepted);
    return ResponseEntity.ok(
      ApiResponse.success(HttpStatus.OK.value(),
      "Friendship request accepted.",
      String.format("'%s' accepted the friend request of '%s'.", accepter, accepted)));
  }

  @PostMapping("/friend-request/reject")
  public ResponseEntity<ApiResponse<?>> rejectFriendshipRequest(@Valid @RequestBody RejectFriendshipRequestDTO request) {
    String rejecter = request.getRejecterUsername();
    String rejected = request.getRejectedUsername();
    friendsService.rejectFriendshipRequest(rejecter, rejecter);
    return ResponseEntity.ok(
      ApiResponse.success(HttpStatus.OK.value(),
      "Friendship request rejected.",
      String.format("'%s' rejected the friend request of '%s'.", rejecter, rejected)));
  }
}
