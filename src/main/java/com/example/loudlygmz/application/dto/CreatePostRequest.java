package com.example.loudlygmz.application.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
  private String gameName;
  private String postContent;
}
