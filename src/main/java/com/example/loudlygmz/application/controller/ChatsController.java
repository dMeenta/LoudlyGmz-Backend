package com.example.loudlygmz.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.domain.model.ChatMessage;
import com.example.loudlygmz.domain.service.IChatsService;
import com.example.loudlygmz.domain.service.IMongoUserService;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.common.ChatUtils;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final IMongoUserService mongoUserService;

  @Operation(
        summary = "List All Messages endpoint",
        description = "Endpoint to list all messages registered on database between two users"
    )
  @GetMapping(value = "/messages/{usernameToChat}")
  public ResponseEntity<ResponseDTO<Page<ChatMessage>>> getMessages(
    @PathVariable String usernameToChat,
    @RequestParam(defaultValue = "0") int offset,
    @RequestParam(defaultValue = "20") int limit) {
      String userLogged = AuthUtils.getCurrentUser().getUid();
      String userToChat = mongoUserService.getUserByUsername(usernameToChat).getId();

      String chatId = ChatUtils.buildChatId(userLogged, userToChat);

      Page<ChatMessage> response = chatsService.getMessagesByChatId(chatId, offset, limit);
      return ResponseEntity.ok(
        ResponseDTO.success(
        HttpStatus.OK.value(),
        String.format("Mensajes del chat entre %s y %s listados", userLogged, userToChat),
        response));
  }

  
}
