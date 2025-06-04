package com.example.loudlygmz.domain.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "chat_messages")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class ChatMessage {
  
  @Id
  private String id;

  private String chatId;
  private String sender;
  private String receiver;
  private String content;
  private Instant timestamp;
}
