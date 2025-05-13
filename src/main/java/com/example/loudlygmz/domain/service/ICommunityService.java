package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.domain.model.Community;

public interface ICommunityService {
    Community createCommunity(Integer gameId);
    Community addMember(Integer gameId, String userId);
    void removeMember(Integer gameId, String userId);
    /*public ResponseEntity<?> checkMembership(String userId, Integer gameId);
    List<GameResponse> getCommunitiesByUserId(String userId); */
}
