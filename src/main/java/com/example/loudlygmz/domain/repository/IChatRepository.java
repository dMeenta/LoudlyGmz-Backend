package com.example.loudlygmz.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.ChatMessage;

public interface IChatRepository extends MongoRepository<ChatMessage, String>{
  List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);
}
