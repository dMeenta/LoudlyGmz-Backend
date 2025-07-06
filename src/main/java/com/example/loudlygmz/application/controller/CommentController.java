package com.example.loudlygmz.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.CommentDTO;
import com.example.loudlygmz.application.dto.CreateCommentRequest;
import com.example.loudlygmz.domain.model.Comment;
import com.example.loudlygmz.domain.service.ICommentService;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final ICommentService commentService;

  @PostMapping()
  public ResponseEntity<ResponseDTO<Comment>> makeAComment(@RequestBody CreateCommentRequest request) {
    String usernameLogged = AuthUtils.getCurrentUser().getUsername();
    String userProfilePictureLogged = AuthUtils.getCurrentUser().getProfilePicture();
    Comment response = commentService.createComment(request.getPostId(), usernameLogged, userProfilePictureLogged, request.getCommentContent());
    return ResponseEntity.ok(
      ResponseDTO.success(
      HttpStatus.OK.value(),
      "Comment published successfully!",
      response));
  }
      
  @GetMapping("/{postId}")
  public ResponseEntity<ResponseDTO<Page<CommentDTO>>> findPostsByCommunityName(
    @PathVariable String postId,
    @RequestParam(defaultValue = "0") int offset,
    @RequestParam(defaultValue = "5") int limit) {
      String usernameLogged = AuthUtils.getCurrentUser().getUsername();
      Page<CommentDTO> response = commentService.findByPostIdPage(postId, usernameLogged, offset, limit);
      return ResponseEntity.ok(
        ResponseDTO.success(
        HttpStatus.OK.value(),
        String.format("Comments of Post with Id: %s listed from %s to %s!", postId, offset, limit),
        response));
  }

  @PatchMapping("/{commentId}/toggle-like")
  public ResponseEntity<ResponseDTO<Comment>> toggleLike(@PathVariable String commentId){
    String usernameLogged = AuthUtils.getCurrentUser().getUsername();
    Comment response = commentService.toggleLike(commentId, usernameLogged);
    return ResponseEntity.ok(
      ResponseDTO.success(
      HttpStatus.OK.value(),
      "Like status updated!",
      response));
  }

}
