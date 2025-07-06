package com.example.loudlygmz.domain.service;

import org.springframework.data.domain.Page;

import com.example.loudlygmz.application.dto.CommentDTO;
import com.example.loudlygmz.domain.model.Comment;

public interface ICommentService {
  Comment createComment(String postId, String usernameLogged, String userProfilePictureLogged, String commentContent);
  Page<CommentDTO> findByPostIdPage(String postId, String usernameLogged, int offset, int limit);
  Comment toggleLike(String commentId, String usernameLogged);
}
