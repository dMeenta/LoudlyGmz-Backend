package com.example.loudlygmz.application.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserMessagerInfo {
  String uid;
  String username;
}
