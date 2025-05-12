package com.example.loudlygmz.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.loudlygmz.infrastructure.common.ApiResponse;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null)
        );
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<ApiResponse<Object>> handleFirebaseError(FirebaseAuthException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Error de Firebase: " + ex.getMessage(),
                null)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(
            ApiResponse.error(
                HttpStatus.BAD_REQUEST.value(),
                "Validación fallida: " + errorMessage,
                null)
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ApiResponse.error(
            HttpStatus.UNAUTHORIZED.value(),
            ex.getMessage(),
            null
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error inesperado: " + ex.getMessage(),
                null)
        );
    }

}
