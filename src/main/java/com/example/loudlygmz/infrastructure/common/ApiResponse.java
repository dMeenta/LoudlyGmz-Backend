package com.example.loudlygmz.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private T data;

    // Constructor para respuestas exitosas
    public static <T> ApiResponse<T> success(int statusCode, String message, T data) {
        return new ApiResponse<>(statusCode, true, message, data);
    }

    // Constructor para respuestas de error
    public static <T> ApiResponse<T> error(int statusCode, String message, T data) {
        return new ApiResponse<>(statusCode, false, message, data);
    }
}
