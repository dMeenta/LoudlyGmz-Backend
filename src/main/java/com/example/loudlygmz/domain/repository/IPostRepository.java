package com.example.loudlygmz.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.loudlygmz.domain.model.Post;

public interface IPostRepository  extends MongoRepository<Post, String> {
  Page<Post> findByCommunityName(String communityName, Pageable pageable);
  Page<Post> findByCommunityNameIn(List<String> communityName, Pageable pageable);
}
