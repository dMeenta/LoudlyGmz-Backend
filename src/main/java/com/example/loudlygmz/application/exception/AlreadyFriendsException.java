package com.example.loudlygmz.application.exception;

public class AlreadyFriendsException extends FriendshipException {

  public AlreadyFriendsException() {
    super("You two are already friends.");
  }
}
