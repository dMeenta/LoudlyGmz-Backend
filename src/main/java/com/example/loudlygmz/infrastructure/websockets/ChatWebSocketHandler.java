package com.example.loudlygmz.infrastructure.websockets;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.loudlygmz.application.dto.chats.ChatMessageDTO;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.repository.IChatRepository;
import com.example.loudlygmz.infrastructure.common.ChatUtils;
import com.example.loudlygmz.infrastructure.orchestrator.UserOrchestrator;
import com.example.loudlygmz.infrastructure.service.impl.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final IChatRepository chatRepository;
  private final UserOrchestrator userOrchestrator;
  private final AuthService authService;
  
  private final Validator validator;

  private final ObjectMapper objectMapper;
  private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();
  private final Map<String, String> sessionUidMap = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session){
    try {
      String idToken = extractIdToken(session);
      String uid = authService.verifyIdToken(idToken);

      userOrchestrator.getUserByUid(uid);

      activeSessions.put(uid, session);
      sessionUidMap.put(session.getId(), uid);
    } catch (Exception e) {
      log.error("Connection rejected: {}", e.getMessage(), e);
        try {
            session.sendMessage(new TextMessage("Connection rejected: " + e.getMessage()));
            session.close(CloseStatus.BAD_DATA);
        } catch (Exception ignored) {}
    }
    

  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage messageText) {
    try {
      String uid = sessionUidMap.get(session.getId());
      if(uid == null){
        session.sendMessage(new TextMessage("Unauthorized."));
        session.close();
        return;
      }
      
      UserResponse senderUser = userOrchestrator.getUserByUid(uid);
      
      try {
        ChatMessageDTO dto = objectMapper.readValue(messageText.getPayload(), ChatMessageDTO.class);

        Set<ConstraintViolation<ChatMessageDTO>> violations = validator.validate(dto);
        if(!violations.isEmpty()){
          String errorMssg = violations.stream()
            .map(ConstraintViolation::getMessage)
            .findFirst()
            .orElse("Invalid input.");
          session.sendMessage(new TextMessage("Validation error: " + errorMssg));
          return;
        }

        boolean areFriends = senderUser.getFriendsList().stream()
        .anyMatch(f -> f.friendUid().equals(dto.getReceiver()));
      
        if(!areFriends){
          session.sendMessage(new TextMessage("You only can chat with your friends!"));
          return;
        }

        ChatMessage message = buildMessage(uid, dto);

        chatRepository.save(message);

        WebSocketSession receiverSession = activeSessions.get(message.getReceiver());
        if(receiverSession != null && receiverSession.isOpen()){
          String json = objectMapper.writeValueAsString(message);
          receiverSession.sendMessage(new TextMessage(json));
        }
      } catch (JsonProcessingException jsonEx){
        log.warn("Error parsing JSON: {}", jsonEx.getOriginalMessage());
        session.sendMessage(new TextMessage("Invalid message format. Review the JSON sent"));
      }
       catch (Exception e) {
        log.error("Unexpected error at message handling", e);
        session.sendMessage(new TextMessage("An unexpected error occurred."));
      }
    } catch (Exception ex) {
      log.error("WebSocket error while handling message: {}", ex.getMessage(), ex);
      try {
        session.sendMessage(new TextMessage("Error: " + ex.getMessage()));
      } catch (Exception ignored) {
        log.warn("Failed to send error message to client", ignored);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    String uid = sessionUidMap.remove(session.getId());
    if(uid != null){
      activeSessions.remove(uid);
    }
  }

  private String extractIdToken(WebSocketSession session) {
    String query = session.getUri().getQuery();
    if (query == null || !query.startsWith("idToken=")) {
        throw new RuntimeException("Token not found on WebSocket connection.");
    }
    return query.replace("idToken=", "");
  }

  private ChatMessage buildMessage(String sender, ChatMessageDTO dto){
    return ChatMessage.builder()
      .sender(sender)
      .receiver(dto.getReceiver())
      .content(dto.getContent())
      .timestamp(Instant.now())
      .chatId(ChatUtils.buildChatId(sender, dto.getReceiver()))
      .build();
  }
}
