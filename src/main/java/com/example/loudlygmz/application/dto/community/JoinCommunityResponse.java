package com.example.loudlygmz.application.dto.community;

import java.time.Instant;

import lombok.Data;

@Data
public class JoinCommunityResponse {
    private Integer communityId;
    private String username, communityName, communityCard;
    private Instant joinedAt;
}
