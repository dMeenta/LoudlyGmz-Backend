package com.example.loudlygmz.application.exception;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message){
    super(message);
  }
}
