package com.example.loudlygmz.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.service.IChatsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(
    name = "Chats Controller",
    description = "Controller to list all the messages of two users"
)
@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatsController {
  private final IChatsService chatsService;

  @Operation(
        summary = "List All Messages endpoint",
        description = "Endpoint to list all messages registered on database between two users"
    )
  @GetMapping(value = "/{chatId}/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public List<ChatMessage> getMessages(@Schema(example = "user1Id-user2Id") @PathVariable String chatId) {
      return chatsService.getMessagesByChatId(chatId);
  }
}
