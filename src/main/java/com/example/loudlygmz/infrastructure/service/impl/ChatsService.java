package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.repository.IChatRepository;
import com.example.loudlygmz.domain.service.IChatsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatsService implements IChatsService{

  private final IChatRepository chatRepository;

  @Override
  public ChatMessage save(ChatMessage chatMessage) {
    return chatRepository.save(chatMessage);
  }

  @Override
  public List<ChatMessage> getMessagesByChatId(String chatId) {
    return chatRepository.findByChatIdOrderByTimestampAsc(chatId);
  }
  
}
