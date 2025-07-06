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

import com.example.loudlygmz.application.dto.PostDTO;
import com.example.loudlygmz.domain.model.Post;
import com.example.loudlygmz.domain.repository.IPostRepository;
import com.example.loudlygmz.domain.service.IPostService;
import com.example.loudlygmz.infrastructure.orchestrator.CommunityOrchestrator;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService implements IPostService {

  private final IPostRepository postRepository;
  private final CommunityOrchestrator communityOrchestrator;

  @Override
  public Post createPost(String gameName, String usernameLogged, String userProfilePictureLogged, String postContent){
    Post post = new Post();
    post.setCommunityName(gameName);
    post.setPosterUsername(usernameLogged);
    post.setPosterProfilePicture(userProfilePictureLogged);
    post.setLikes(new ArrayList<String>());
    post.setContent(postContent);
    post.setPostedAt(Instant.now());
    
    return postRepository.insert(post);
  }

  @Override
  public Page<PostDTO> findByCommunityNamePage(String communityName, String usernameLogged, int offset, int limit){
    Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("postedAt").descending());

    Page<Post> postsPage = postRepository.findByCommunityName(communityName, pageable);

    List<PostDTO> postDTOs = postsPage.getContent().stream()
      .map(post -> {
        boolean likedByCurrentUser = post.getLikes().contains(usernameLogged);

        // Construye y devuelve el PostDTO
        return PostDTO.builder()
          .id(post.getId())
          .communityName(post.getCommunityName())
          .posterUsername(post.getPosterUsername())
          .posterProfilePicture(post.getPosterProfilePicture())
          .likes(post.getLikes())
          .content(post.getContent())
          .postedAt(post.getPostedAt())
          .currentUserLikedIt(likedByCurrentUser)
          .build();
        }).collect(Collectors.toList());

        return new PageImpl<>(postDTOs, pageable, postsPage.getTotalElements());
  }

  @Override
  public Post toggleLike(String postId, String usernameLogged) {
    Post post = postRepository.findById(postId).orElseThrow(()-> 
    new EntityNotFoundException(String.format("Post with ID: %s doesn't exist", postId)));

    if (post.getLikes() == null) {
            post.setLikes(new ArrayList<>());
    }

    if(post.getLikes().contains(usernameLogged)){
      post.getLikes().remove(usernameLogged);
    }else{
      post.getLikes().add(usernameLogged);
    }

    return postRepository.save(post);
  }

  @Override
  public Page<PostDTO> getUserFeed(String usernameLogged, int offset, int limit) {
    List<String> userCommunitiesName = communityOrchestrator.getCommunityNamesUserLogged(usernameLogged);

    if(userCommunitiesName.isEmpty()){
      return Page.empty();
    }

    Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("postedAt").descending());

    Page<Post> postsPage = postRepository.findByCommunityNameIn(userCommunitiesName, pageable);

    List<PostDTO> postDTOs = postsPage.getContent().stream()
    .map(post -> {
      boolean likedByCurrentUser = post.getLikes().contains(usernameLogged);
      return PostDTO.builder()
        .id(post.getId())
        .communityName(post.getCommunityName())
        .posterUsername(post.getPosterUsername())
        .posterProfilePicture(post.getPosterProfilePicture())
        .likes(post.getLikes())
        .content(post.getContent())
        .postedAt(post.getPostedAt())
        .currentUserLikedIt(likedByCurrentUser)
        .build();}).toList();

      return new PageImpl<>(postDTOs, pageable, postsPage.getTotalElements());
  }
}
