package com.example.loudlygmz.infrastructure.websockets;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.loudlygmz.application.dto.chats.ChatMessageDTO;
import com.example.loudlygmz.application.dto.chats.ChatMessageResponseDTO;
import com.example.loudlygmz.application.dto.user.UserMessagerInfo;
import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.repository.IChatRepository;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.infrastructure.common.ChatMessageMapper;
import com.example.loudlygmz.infrastructure.common.ChatMessageMapper.ValidationException;
import com.example.loudlygmz.infrastructure.orchestrator.UserOrchestrator;
import com.example.loudlygmz.infrastructure.service.impl.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final IChatRepository chatRepository;
  private final UserOrchestrator userOrchestrator;
  private final IMongoUserService mongoUserService;
  private final AuthService authService;

  private final ChatMessageMapper chatMessageMapper;

  private final ObjectMapper objectMapper;
  private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>(); // clave: username
  private final Map<String, String> sessionUsernameMap = new ConcurrentHashMap<>(); // sessionId → username
  private final Map<String, String> sessionUidMap = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session){
    try {
      String idToken = extractIdToken(session);
      String uid = authService.verifyIdToken(idToken);

      UserResponse user = userOrchestrator.getUserByUid(uid);
      String username = user.getUsername();

      activeSessions.put(username, session);
      sessionUsernameMap.put(session.getId(), username);
      sessionUidMap.put(session.getId(), uid);
      log.info("WebSocket connection established for user UID: {}", username);
    } catch (Exception e) {
      log.error("Connection rejected: {}", e.getMessage(), e);
        try {
          safelySendMessage(session, "Connection rejected: " + e.getMessage());
          session.close(CloseStatus.BAD_DATA);
        } catch (Exception ignored) {}
    }
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage messageText) {
    try {
      String uid = sessionUidMap.get(session.getId());
      if(uid == null){
        rejectUnauthorized(session);
        return;
      }
      
      UserResponse senderUser = userOrchestrator.getUserByUid(uid);
      
      ChatMessageDTO dto = parseAndValidateDTO(session, messageText.getPayload());
      if (dto==null) return;

      boolean isTheSameUser = senderUser.getUsername().equals(dto.getReceiverUsername());

      if(isTheSameUser){
        safelySendMessage(session, "You can not chat with yourself.");
        return;
      }
      
      if(!areFriends(senderUser, dto.getReceiverUsername())){
        safelySendMessage(session, "You only can chat with your friends!");
        return;
      }

      String receiverUid = mongoUserService.getUserByUsername(dto.getReceiverUsername()).getId();

      UserMessagerInfo senderInfo = UserMessagerInfo.builder()
      .uid(uid).username(senderUser.getUsername()).build();
      UserMessagerInfo receiverInfo = UserMessagerInfo.builder()
      .uid(receiverUid).username(dto.getReceiverUsername()).build();

      ChatMessage message = chatMessageMapper.buildMessage(senderInfo, receiverInfo, dto.getContent());

      chatRepository.save(message);

      sendMessageToReceiver(message);
    } catch (Exception ex) {
      log.error("WebSocket error while handling message: {}", ex.getMessage(), ex);
      safelySendMessage(session, "Error: " + ex.getMessage());
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    String username = sessionUsernameMap.remove(session.getId());
    if(username != null){
      activeSessions.remove(username);
      log.info("WebSocket connection closed for username: {}", username);
    }
  }

  // ============ Utility Methods ===============

  private void safelySendMessage(WebSocketSession session, String message) {
    try {
      session.sendMessage(new TextMessage(message));
    } catch (Exception e) {
      log.warn("Failed to send error message to client", e);
    }
  }

  private String extractIdToken(WebSocketSession session) {
    var cookies = session.getHandshakeHeaders().get("cookie");
    if (cookies == null || cookies.isEmpty()) {
        throw new RuntimeException("No cookies found in WebSocket connection.");
    }

    for(String cookieHeader: cookies){
      String[] cookiePairs = cookieHeader.split(";");
      for(String pair: cookiePairs){
        String[] keyValue = pair.trim().split("=", 2);
        if(keyValue.length==2 && keyValue[0].equals("session")){
          return keyValue[1];
        }
      }
    }

    throw new RuntimeException("Session cookie not found.");
  }

  private void rejectUnauthorized(WebSocketSession session) throws Exception{
    safelySendMessage(session, "Unauthorized.");
    session.close();
  }

  private ChatMessageDTO parseAndValidateDTO(WebSocketSession session, String payload){
    try {
      ChatMessageDTO dto = chatMessageMapper.parseAndValidate(payload, objectMapper);
      log.info("Parsed and validated ChatMessageDTO: {}", dto);
      return dto;
    } catch (ValidationException vex){
      safelySendMessage(session, "Validation error:" + vex.getMessage());
      log.error("Validation error: {}", vex.getMessage());
      return null;
    }
  }

  private boolean areFriends(UserResponse sender, String receiverUsername){
    String receiverUid = mongoUserService.getUserByUsername(receiverUsername).getId();
    return sender.getFriendsList().stream().anyMatch(f->f.friendUid().equals(receiverUid));
  }

  private void sendMessageToReceiver(ChatMessage message){
    WebSocketSession receiverSession = activeSessions.get(message.getReceiver());
    if(receiverSession != null && receiverSession.isOpen()){
      try {
        ChatMessageResponseDTO responseDTO = chatMessageMapper.toResponseDTO(message);
        String json = objectMapper.writeValueAsString(responseDTO);
        safelySendMessage(receiverSession, json);
        log.info("Message sent to receiver {} via WebSocket.", message.getReceiver());
      } catch (Exception e) {
        log.warn("Failed to send message to receiver {}", message.getReceiver(), e);
      }
    } else {
      log.info("Receiver {} is offline. Message stored in database.", message.getReceiver());
    }
  }

}
