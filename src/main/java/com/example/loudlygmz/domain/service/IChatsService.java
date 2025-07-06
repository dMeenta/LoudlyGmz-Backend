package com.example.loudlygmz.domain.service;

import org.springframework.data.domain.Page;

import com.example.loudlygmz.domain.model.ChatMessage;

public interface IChatsService {
  ChatMessage save(ChatMessage chatMessage);
  Page<ChatMessage> getMessagesByChatId(String chatId, int offset, int limit);
}
