package com.example.loudlygmz.application.dto.community;

import java.time.Instant;

import com.example.loudlygmz.domain.enums.CommunityAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommunityMembershipResponse {
    private CommunityAction action;
    private String username;
    private Integer gameId;
    private String communityName;
    private Instant instant;

    @JsonIgnore
    public String getMessage() {
        return String.format("%s %s la comunidad de %s.", username, action.toMessageVerb(), communityName);
    }
}
