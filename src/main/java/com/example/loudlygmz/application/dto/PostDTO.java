package com.example.loudlygmz.application.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
  private String id;
  private String communityName;
  private String posterUsername;
  private String posterProfilePicture;
  private List<String> likes;
  private String content;
  private boolean currentUserLikedIt;
  @JsonProperty("isOwner")
  private boolean isOwner;
  private Instant postedAt;
}
