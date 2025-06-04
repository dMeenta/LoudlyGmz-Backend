package com.example.loudlygmz.domain.service;

import java.util.List;

import com.example.loudlygmz.domain.model.ChatMessage;

public interface IChatsService {
  ChatMessage save(ChatMessage chatMessage);
  List<ChatMessage> getMessagesByChatId(String chatId);
}
