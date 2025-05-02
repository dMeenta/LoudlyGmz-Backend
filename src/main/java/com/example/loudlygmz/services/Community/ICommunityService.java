package com.example.loudlygmz.services.Community;

import org.springframework.http.ResponseEntity;

import com.example.loudlygmz.entity.CommunityRequests;

public interface ICommunityService {
    public ResponseEntity<?> joinCommunity(CommunityRequests request);
    public ResponseEntity<?> leaveCommunity(CommunityRequests request);
    public ResponseEntity<?> checkMembership(String userId, Integer gameId);
}
