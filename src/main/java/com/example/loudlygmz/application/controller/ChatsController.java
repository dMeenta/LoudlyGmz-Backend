package com.example.loudlygmz.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.service.IChatsService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatsController {
  private final IChatsService chatsService;

  @GetMapping(value = "/{chatId}/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public List<ChatMessage> getMessages(@PathVariable String chatId) {
      return chatsService.getMessagesByChatId(chatId);
  }
  
}
