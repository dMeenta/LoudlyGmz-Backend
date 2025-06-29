package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IFriendsService {
  void sendFriendshipRequest(String requesterUsername, String targetUsername);
  void cancelFriendshipRequest(String cancellerUsername, String cancelledUsername);

  void rejectFriendshipRequest(String rejecterUsername, String rejectedUsername);
  void acceptFriendshipRequest(String accepterUsername, String acceptedUsername);
  
  void validateFriendshipRequest(MongoUser sender, MongoUser receiverId);

  void addSentFriendshipRequest(MongoUser sender, String receiverUsername);
  void removeSentFriendRequest(MongoUser sender, String receiverUsername);
  
  void addReceivedFriendshipRequest(MongoUser receiver, String senderUsername);
  void removeReceivedFriendshipRequest(MongoUser receiver, String senderUsername);

}
