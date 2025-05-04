package com.example.loudlygmz.DAO.Community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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
            Timestamp now = Timestamp.now();
            DocumentReference communityRef = firestore.collection("communities").document(gameId.toString());
            DocumentReference userRef = firestore.collection("users").document(userId);
            DocumentReference memberRef = communityRef.collection("members").document(userId);

            // Verificar si ya es miembro
            DocumentSnapshot memberSnap = memberRef.get().get();
            if (memberSnap.exists()) {
                throw new RuntimeException("El usuario ya está en la comunidad");
            }

            // 1. Añadir al documento de comunidad
            Map<String, Object> memberData = new HashMap<>();
            memberData.put("joinedAt", FieldValue.serverTimestamp());
            memberRef.set(memberData);

            // 2. Añadir al documento del usuario
            DocumentSnapshot userSnap = userRef.get().get();

            Map<String, Object> newCommunity = new HashMap<>();
            newCommunity.put("gameId", gameId.toString());
            newCommunity.put("joinedAt", now);

            if (userSnap.exists()) {
                userRef.update("joinedCommunities", FieldValue.arrayUnion(newCommunity));
            } else {
                userRef.set(Map.of("joinedCommunities", List.of())); // crear documento vacío
                userRef.update("joinedCommunities", FieldValue.arrayUnion(newCommunity)); // luego añadir
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error al unirse a la membresía", e);
        }
    }

    @Override
    public boolean leaveCommunity(String userId, Integer gameId) {
        try {
            DocumentReference communityRef = firestore.collection("communities").document(gameId.toString());
            DocumentReference userRef = firestore.collection("users").document(userId);
            DocumentReference memberRef = communityRef.collection("members").document(userId);

            // 1. Eliminar al usuario de la comunidad
            memberRef.delete();

            // 2. Eliminar la comunidad del documento del usuario
            DocumentSnapshot userSnap = userRef.get().get();
            if(userSnap.exists() && userSnap.contains("joinedCommunities")){
                List<Map<String, Object>> joinedCommunities = (List<Map<String, Object>>) userSnap.get("joinedCommunities");
                List<Map<String, Object>> updated = joinedCommunities.stream()
                .filter(c -> !c.get("gameId").toString().equals(gameId.toString()))
                .toList();
                userRef.update("joinedCommunities", updated);
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error al abandonar la comunidad", e);
        }
    }

    @Override
    public boolean checkMembership(String userId, Integer gameId) {
        try {
            DocumentReference memberRef = firestore
            .collection("communities")
            .document(gameId.toString())
            .collection("members")
            .document(userId);

            DocumentSnapshot snapshot = memberRef.get().get();
            return snapshot.exists();
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar la membresía", e);
        }
    }

    @Override
    public List<?> getCommunitiesByUser(String uid) {
        try {
            DocumentSnapshot snapshot = firestore
            .collection("users")
            .document(uid).get().get();

            if (snapshot.exists() && snapshot.contains("joinedCommunities")) {
                return (List<Map<String, Object>>) snapshot.get("joinedCommunities");
            } else {
                return List.of(); // vacío si no hay comunidades
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las comunidades del usuario", e);
        }
    }
    
}
