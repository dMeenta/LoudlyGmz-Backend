package com.example.loudlygmz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.entity.CommunityRequests;
import com.example.loudlygmz.services.Community.ICommunityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/community")
public class CommunityController {
    
    @Autowired
    private ICommunityService communityService;

    @PostMapping("/join")
    public ResponseEntity<?> joinCommunity(@RequestBody CommunityRequests request) {
        return communityService.joinCommunity(request);
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveCommunity(@RequestBody CommunityRequests request) {
        return communityService.leaveCommunity(request);
    }

    @GetMapping("/isMember")
    public ResponseEntity<?> checkMembership(@RequestParam String userId, @RequestParam Integer gameId) {
        return communityService.checkMembership(userId, gameId);
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<?> getCommunitiesByUser(@PathVariable String uid) {
        return communityService.getCommunitiesByUser(uid);
    }
    
}
