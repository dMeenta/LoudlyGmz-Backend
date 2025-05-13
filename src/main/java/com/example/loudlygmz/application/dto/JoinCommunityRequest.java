package com.example.loudlygmz.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinCommunityRequest {
    
    @NotNull
    private String userId;
    @NotNull
    private Integer gameId;
    
}
