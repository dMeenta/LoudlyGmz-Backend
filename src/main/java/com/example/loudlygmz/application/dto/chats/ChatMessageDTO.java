package com.example.loudlygmz.application.dto.chats;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessageDTO {

  @NotBlank(message = "Receiver username cannot b blank.")
  private String receiverUsername;
  @NotBlank(message = "Message content cannot b blank.")
  private String content;
}
