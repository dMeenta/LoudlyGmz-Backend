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

@Document(collection = "communities")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Community {

    @Id
    private Integer gameId;
    private List<Member> members;

    public record Member(String userId, Instant joinedAt) {}
    
}
