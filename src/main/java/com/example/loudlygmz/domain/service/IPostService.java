package com.example.loudlygmz.domain.service;

import org.springframework.data.domain.Page;

import com.example.loudlygmz.application.dto.PostDTO;
import com.example.loudlygmz.domain.model.Post;

public interface IPostService {
  Post createPost(String gameName, String usernameLogged, String userProfilePictureLogged, String postContent);
  Page<PostDTO> findByCommunityNamePage(String communityName, String usernameLogged, int offset, int limit);
  Post toggleLike(String postId, String usernameLogged);
  Page<PostDTO> getUserFeed(String usernameLogged, int offset, int limit);
}
