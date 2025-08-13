package com.projectz.common.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    T data,
    String message,
    LocalDateTime timestamp,
    boolean success
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "OK", LocalDateTime.now(), true);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, LocalDateTime.now(), true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, LocalDateTime.now(), false);
    }
}
