package com.detyra.miniiotsystem.controller.advice;

import com.detyra.miniiotsystem.exception.ModelErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ModelErrorMessage> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request){
        var resp = ModelErrorMessage.builder()
                .message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ModelErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String enumClassName = extractEnumClassName(ex.getMessage());
        String enumName=extractEnumName(enumClassName);
        Enum<?>[] enumValues = getEnumValues(enumClassName);
        String validEnumValues = getValidEnumValues(enumValues);
        String errorMessage = "Invalid value for " + enumName + ". Supported values are: " + validEnumValues;

        var resp = ModelErrorMessage.builder()
                .message(errorMessage).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }

    private String extractEnumClassName(String errorMessage) {
        int startIndex = errorMessage.indexOf('`');
        int endIndex = errorMessage.lastIndexOf('`');
        return errorMessage.substring(startIndex + 1, endIndex);
    }

    private String extractEnumName(String fullyQualifiedClassName) {
        String[] parts = fullyQualifiedClassName.split("\\.");
        return parts[parts.length - 1];
    }

    private Enum<?>[] getEnumValues(String enumClassName) {
        try {
            Class<?> enumClass = Class.forName(enumClassName);
            if (enumClass.isEnum()) {
                return (Enum<?>[]) enumClass.getEnumConstants();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getValidEnumValues(Enum<?>[] enumValues) {
        return Arrays.stream(enumValues)
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelErrorMessage> handleConstraintViolationException(Exception ex, HttpServletRequest request) {
        var resp = ModelErrorMessage.builder()
                .message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }

}
