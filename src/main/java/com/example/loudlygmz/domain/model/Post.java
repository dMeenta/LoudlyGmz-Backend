package com.example.loudlygmz.domain.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "posts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Post {
    @Id
    private String id;

    private String communityName;
    private String posterUsername;
    private String posterProfilePicture;
    private List<String> likes;
    private String content;
    private Instant postedAt;
}

