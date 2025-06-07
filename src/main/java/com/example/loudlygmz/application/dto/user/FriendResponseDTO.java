package com.example.loudlygmz.application.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendResponseDTO {
  private String friendUid;
  private String friendUsername;
  private String friendProfilePicture;
}
