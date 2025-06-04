package com.example.loudlygmz.application.dto.chats;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessageDTO {

  @NotBlank(message = "Receiver UID cannot b blank.")
  private String receiver;
  @NotBlank(message = "Message content cannot b blank.")
  private String content;
}
