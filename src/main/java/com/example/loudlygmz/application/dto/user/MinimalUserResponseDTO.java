package com.example.loudlygmz.application.dto.user;

import com.example.loudlygmz.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinimalUserResponseDTO {
  private String username;
  private String profilePicture;
  private Role role;
}
