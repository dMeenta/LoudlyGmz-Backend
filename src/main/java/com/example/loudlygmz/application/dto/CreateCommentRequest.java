package com.example.loudlygmz.application.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {
  private String postId;
  private String commentContent;
}
