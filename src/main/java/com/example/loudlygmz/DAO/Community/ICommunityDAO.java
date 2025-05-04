package com.example.loudlygmz.DAO.Community;

import java.util.List;

public interface ICommunityDAO {
    boolean joinCommunity(String userId, Integer gameId);
    boolean leaveCommunity(String userId, Integer gameId);
    boolean checkMembership(String userId, Integer gameId);
    List<?> getCommunitiesByUser(String uid);
}
