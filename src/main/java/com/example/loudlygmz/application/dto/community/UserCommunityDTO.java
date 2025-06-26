package com.example.loudlygmz.application.dto.community;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCommunityDTO {
  private Integer id;
  private String name;
  private Instant memberSince;
  private String card;
}
