package com.example.loudlygmz.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private T data;

    // Constructor para respuestas exitosas
    public static <T> ResponseDTO<T> success(int statusCode, String message, T data) {
        return new ResponseDTO<>(statusCode, true, message, data);
    }

    // Constructor para respuestas de error
    public static <T> ResponseDTO<T> error(int statusCode, String message, T data) {
        return new ResponseDTO<>(statusCode, false, message, data);
    }
}
