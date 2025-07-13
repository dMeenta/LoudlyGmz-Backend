package com.example.loudlygmz.application.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.loudlygmz.infrastructure.common.ResponseDTO;
import com.google.firebase.auth.FirebaseAuthException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ResponseDTO<?>> handleDuplicateEmailException(DuplicateEmailException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ResponseDTO.error(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ResponseDTO<?>> handleDuplicateEmailException(DuplicateUsernameException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ResponseDTO.error(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null));
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> handleNotFound(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ResponseDTO.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
            ResponseDTO.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null)
        );
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<ResponseDTO<Object>> handleFirebaseError(FirebaseAuthException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ResponseDTO.error(
                HttpStatus.BAD_REQUEST.value(),
                "Error de Firebase",
                ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
            ResponseDTO.error(
                HttpStatus.BAD_REQUEST.value(),
                "Validación fallida",
                errorMessage
            )
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseDTO<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        ResponseDTO.error(
            HttpStatus.UNAUTHORIZED.value(),
            ex.getMessage(),
            null
        ));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDTO<Object>> handleDataAccess(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ResponseDTO.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error al acceder a la base de datos",
                ex.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleCategoryValidationException(CategoryValidationException ex) {
        ResponseDTO<Map<String, String>> response = ResponseDTO.error(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            ex.getValidationMap()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FriendshipException.class)
    public ResponseEntity<ResponseDTO<String>> handleFriendshipException(FriendshipException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseDTO.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDTO<Object>> handleUnauthorizedException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ResponseDTO.error(
                HttpStatus.UNAUTHORIZED.value(),
                "Token invalido o usuario no autenticado.",
                ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ResponseDTO.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error inesperado",
                ex.getMessage())
        );
    }
}
