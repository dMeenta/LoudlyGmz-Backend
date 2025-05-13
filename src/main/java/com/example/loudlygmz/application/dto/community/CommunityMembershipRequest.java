package com.example.loudlygmz.application.dto.community;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommunityMembershipRequest {
    
    @NotNull
    private String userId;
    @NotNull
    private Integer gameId;
    
}
