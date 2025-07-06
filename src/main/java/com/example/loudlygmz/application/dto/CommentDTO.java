package com.example.loudlygmz.application.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
  private String id;
  private String postId;
  private String commenterUsername;
  private String commenterProfilePicture;
  private String commentContent;
  private List<String> likes;
  private Instant commentedAt;
  private boolean currentUserLikedIt;
}
