package com.example.loudlygmz.infrastructure.common;

import java.time.Instant;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.loudlygmz.application.dto.chats.ChatMessageDTO;
import com.example.loudlygmz.application.dto.chats.ChatMessageResponseDTO;
import com.example.loudlygmz.domain.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatMessageMapper {
  private final Validator validator;

  public ChatMessageDTO parseAndValidate(String json, ObjectMapper objectMapper) throws ValidationException{
    try {
      ChatMessageDTO dto = objectMapper.readValue(json, ChatMessageDTO.class);

      Set<ConstraintViolation<ChatMessageDTO>> violations = validator.validate(dto);
      if(!violations.isEmpty()){
        throw new ValidationException(
          violations.stream().map(ConstraintViolation::getMessage)
          .findFirst().orElse("Invalid input.")
        );
      }
      return dto;
    } catch (JsonProcessingException jsonEx){
      throw new ValidationException("Invalid JSON format");
    }
  }

  public ChatMessage buildMessage(String sender, ChatMessageDTO dto){
    return ChatMessage.builder()
      .sender(sender)
      .receiver(dto.getReceiver())
      .content(dto.getContent())
      .timestamp(Instant.now())
      .chatId(ChatUtils.buildChatId(sender, dto.getReceiver()))
      .build();
  }

  public ChatMessageResponseDTO toResponseDTO(ChatMessage message) {
    return ChatMessageResponseDTO.builder()
      .sender(message.getSender())
      .receiver(message.getReceiver())
      .content(message.getContent())
      .timestamp(message.getTimestamp())
      .build();
  }

  public static class ValidationException extends Exception {
    public ValidationException(String message) {
      super(message);
    }
  }
}
