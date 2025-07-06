package com.example.loudlygmz.infrastructure.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public Page<ChatMessage> getMessagesByChatId(String chatId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("timestamp").descending());
    return chatRepository.findByChatId(chatId, pageable);
  }
  
}
