package com.example.loudlygmz.infrastructure.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.CommentDTO;
import com.example.loudlygmz.domain.model.Comment;
import com.example.loudlygmz.domain.repository.ICommentRepository;
import com.example.loudlygmz.domain.service.ICommentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

  private final ICommentRepository commentRepository;

  @Override
  public Comment createComment(String postId, String usernameLogged, String userProfilePictureLogged,
      String commentContent) {
    Comment comment = new Comment();
    comment.setPostId(postId);
    comment.setCommenterUsername(usernameLogged);
    comment.setCommenterProfilePicture(userProfilePictureLogged);
    comment.setLikes(new ArrayList<String>());
    comment.setCommentContent(commentContent);
    comment.setCommentedAt(Instant.now());
    
    return commentRepository.insert(comment);
  }

  @Override
  public Page<CommentDTO> findByPostIdPage(String postId, String usernameLogged, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("commentedAt").descending());

    Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);

    List<CommentDTO> commentsDTOs = commentsPage.getContent().stream()
      .map(comment -> {
        boolean likedByCurrentUser = comment.getLikes().contains(usernameLogged);

        // Construye y devuelve el PostDTO
        return CommentDTO.builder()
          .id(comment.getId())
          .postId(comment.getPostId())
          .commenterUsername(comment.getCommenterUsername())
          .commenterProfilePicture(comment.getCommenterProfilePicture())
          .likes(comment.getLikes())
          .commentContent(comment.getCommentContent())
          .commentedAt(comment.getCommentedAt())
          .currentUserLikedIt(likedByCurrentUser)
          .build();
        }).collect(Collectors.toList());

        return new PageImpl<>(commentsDTOs, pageable, commentsPage.getTotalElements());
  }

  @Override
  public Comment toggleLike(String commentId, String usernameLogged) {
    Comment comment = commentRepository.findById(commentId).orElseThrow(()-> 
    new EntityNotFoundException(String.format("Comment with ID: %s doesn't exist", commentId)));

    if (comment.getLikes() == null) {
      comment.setLikes(new ArrayList<>());
    }

    if(comment.getLikes().contains(usernameLogged)){
      comment.getLikes().remove(usernameLogged);
    }else{
      comment.getLikes().add(usernameLogged);
    }

    return commentRepository.save(comment);
  }
  
}
