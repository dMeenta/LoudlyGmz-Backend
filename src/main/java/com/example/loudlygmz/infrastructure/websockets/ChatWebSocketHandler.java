package com.example.loudlygmz.infrastructure.websockets;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.loudlygmz.application.dto.user.UserResponse;
import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.repository.IChatRepository;
import com.example.loudlygmz.infrastructure.common.ChatUtils;
import com.example.loudlygmz.infrastructure.orchestrator.UserOrchestrator;
import com.example.loudlygmz.infrastructure.service.impl.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final IChatRepository chatRepository;
  private final UserOrchestrator userOrchestrator;
  private final AuthService authService;
  
  private final ObjectMapper objectMapper;
  private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();
  private final Map<String, String> sessionUidMap = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception{
    String idToken = extractIdToken(session);
    String uid = authService.verifyIdToken(idToken);
    
    userOrchestrator.getUserByUid(uid);

    activeSessions.put(uid, session);
    sessionUidMap.put(session.getId(), uid);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage messageText) throws Exception {
    String uid = sessionUidMap.get(session.getId());
    if(uid == null){
      session.sendMessage(new TextMessage("Unauthorized."));
      session.close();
      return;
    }

    UserResponse senderUser = userOrchestrator.getUserByUid(uid);

    ChatMessage message = objectMapper.readValue(messageText.getPayload(), ChatMessage.class);
    message.setSender(uid);
    message.setTimestamp(Instant.now());
    message.setChatId(ChatUtils.buildChatId(message.getSender(), message.getReceiver()));

    boolean areFriends = senderUser.getFriendsList().stream()
    .anyMatch(f -> f.friendUid().equals(message.getReceiver()));

    if(!areFriends){
      session.sendMessage(new TextMessage("You only can chat with your friends!"));
      return;
    }

    chatRepository.save(message);

    WebSocketSession receiverSession = activeSessions.get(message.getReceiver());
    if(receiverSession != null && receiverSession.isOpen()){
      String json = objectMapper.writeValueAsString(message);
      receiverSession.sendMessage(new TextMessage(json));
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
}
