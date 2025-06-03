package com.example.loudlygmz.application.exception;

public class DuplicateFriendshipRequestException extends FriendshipException {
  public DuplicateFriendshipRequestException() {
    super("You has already sent a friendship request to this user.");
  }
}
