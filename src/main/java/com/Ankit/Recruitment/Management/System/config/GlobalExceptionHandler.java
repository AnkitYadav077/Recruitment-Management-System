package com.Ankit.Recruitment.Management.System.config;


import com.Ankit.Recruitment.Management.System.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error("File size exceeds the maximum limit"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity.badRequest().body(ApiResponse.error("An error occurred: " + e.getMessage()));
    }
}