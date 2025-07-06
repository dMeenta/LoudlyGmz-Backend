package com.example.loudlygmz.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.CreatePostRequest;
import com.example.loudlygmz.application.dto.PostDTO;
import com.example.loudlygmz.domain.model.Post;
import com.example.loudlygmz.domain.service.IPostService;
import com.example.loudlygmz.infrastructure.common.AuthUtils;
import com.example.loudlygmz.infrastructure.common.ResponseDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
  
  private final IPostService postService;
  
  @PostMapping()
  public ResponseEntity<ResponseDTO<Post>> createPost(@RequestBody CreatePostRequest request) {
    String usernameLogged = AuthUtils.getCurrentUser().getUsername();
    String userProfilePictureLogged = AuthUtils.getCurrentUser().getProfilePicture();
    Post response = postService.createPost(request.getGameName(), usernameLogged, userProfilePictureLogged, request.getPostContent());
    return ResponseEntity.ok(
      ResponseDTO.success(
        HttpStatus.OK.value(),
        "Post published successfully!",
        response));
      }
      
      @GetMapping("/{communityName}")
      public ResponseEntity<ResponseDTO<Page<PostDTO>>> findPostsByCommunityName(
        @PathVariable String communityName,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "5") int limit) {
          String usernameLogged = AuthUtils.getCurrentUser().getUsername();
          Page<PostDTO> response = postService.findByCommunityNamePage(communityName, usernameLogged, offset, limit);
          return ResponseEntity.ok(
            ResponseDTO.success(
            HttpStatus.OK.value(),
            String.format("Posts listed from %s to %s!", offset, limit),
            response));
  }

  @PatchMapping("/{postId}/toggle-like")
  public ResponseEntity<ResponseDTO<Post>> toggleLike(@PathVariable String postId){
    String usernameLogged = AuthUtils.getCurrentUser().getUsername();
    Post response = postService.toggleLike(postId, usernameLogged);
    return ResponseEntity.ok(
      ResponseDTO.success(
      HttpStatus.OK.value(),
      "Like status updated!",
      response));
  }

  @GetMapping("/feed")
  public ResponseEntity<ResponseDTO<Page<PostDTO>>> getUserFeed(
    @RequestParam(defaultValue = "0") int offset,
    @RequestParam(defaultValue = "5") int limit
  ) {
    String usernameLogged = AuthUtils.getCurrentUser().getUsername();
    Page<PostDTO> response = postService.getUserFeed(usernameLogged, offset, limit);
    return ResponseEntity.ok(
      ResponseDTO.success(
      HttpStatus.OK.value(),
      "User feed returned!",
      response));
  }
  

}
