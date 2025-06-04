package com.example.loudlygmz.application.dto.chats;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ChatMessageResponseDTO {
  private String sender;
  private String receiver;
  private String content;
  private Instant timestamp;
}
