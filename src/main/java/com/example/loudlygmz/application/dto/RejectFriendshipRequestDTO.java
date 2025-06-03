package com.example.loudlygmz.application.dto;

import lombok.Data;

@Data
public class RejectFriendshipRequestDTO {
  private String rejecterUsername;
  private String rejectedUsername;
}
