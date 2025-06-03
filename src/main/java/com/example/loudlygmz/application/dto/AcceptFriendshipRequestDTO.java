package com.example.loudlygmz.application.dto;

import lombok.Data;

@Data
public class AcceptFriendshipRequestDTO {
  private String accepterUsername;
  private String acceptedUsername;
}
