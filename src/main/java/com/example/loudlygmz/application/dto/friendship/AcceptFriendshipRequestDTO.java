package com.example.loudlygmz.application.dto.friendship;

import lombok.Data;

@Data
public class AcceptFriendshipRequestDTO {
  private String accepterUsername;
  private String acceptedUsername;
}
