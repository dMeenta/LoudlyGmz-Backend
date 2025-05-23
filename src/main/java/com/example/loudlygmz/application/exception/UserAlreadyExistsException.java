package com.example.loudlygmz.application.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super(String.format("Ya existe un usuario con el username '%s'", username));
    }
}
