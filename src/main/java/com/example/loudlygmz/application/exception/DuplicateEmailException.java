package com.example.loudlygmz.application.exception;

public class DuplicateEmailException extends RuntimeException{
  public DuplicateEmailException(String message) {
    super(message);
  }
}
