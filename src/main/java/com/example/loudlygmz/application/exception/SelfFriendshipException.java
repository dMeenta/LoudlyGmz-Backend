package com.example.loudlygmz.application.exception;

public class SelfFriendshipException extends FriendshipException{
  public SelfFriendshipException() {
    super("You can not send a friendship request to yourself.");
  }
}
