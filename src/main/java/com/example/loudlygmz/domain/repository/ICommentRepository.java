package com.example.loudlygmz.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.Comment;

public interface ICommentRepository  extends MongoRepository<Comment, String> {
  Page<Comment> findByPostId(String postId, Pageable pageable);
  void deleteAllByPostId(String postId);
}
