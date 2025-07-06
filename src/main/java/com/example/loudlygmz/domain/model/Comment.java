package com.example.loudlygmz.domain.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "comments")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Comment {
  @Id
  private String id;

  private String postId;
  private String commenterUsername;
  private String commenterProfilePicture;
  private String commentContent;
  private boolean currentUserLikedIt;
  private List<String> likes;
  private Instant commentedAt;
}
