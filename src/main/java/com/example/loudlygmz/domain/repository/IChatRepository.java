package com.example.loudlygmz.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.ChatMessage;

public interface IChatRepository extends MongoRepository<ChatMessage, String>{
  Page<ChatMessage> findByChatId(String chatId, Pageable pageable);
}
