package com.example.loudlygmz.DAO.Community;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

@Repository
public class CommunityDAO implements ICommunityDAO {

    private final Firestore firestore;

    public CommunityDAO(Firestore firestore){
        this.firestore = firestore;
    }

    @Override
    public boolean joinCommunity(String userId, Integer gameId) {
        try {
            DocumentReference communityRef = firestore.collection("communities").document(gameId.toString());
            DocumentReference userRef = firestore.collection("users").document(userId);

            Map<String, Object> memberData = new HashMap<>();
            memberData.put("joinedAt", FieldValue.serverTimestamp());
            communityRef.collection("members").document(userId).set(memberData);

            Map<String, Object> newCommunity = new HashMap<>();
            newCommunity.put("gameId", gameId.toString());
            newCommunity.put("joinedAt", FieldValue.serverTimestamp());

            userRef.update("joinedCommunities", FieldValue.arrayUnion(gameId));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
