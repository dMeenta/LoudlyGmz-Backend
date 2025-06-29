package com.example.loudlygmz.application.dto.user;

import com.example.loudlygmz.domain.enums.FriendshipStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWithRelationshipDTO {
  private String username;
  private String profilePicture;
  private FriendshipStatus friendshipStatus;
}
