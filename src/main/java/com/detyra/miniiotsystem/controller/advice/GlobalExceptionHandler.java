package com.detyra.miniiotsystem.controller.advice;

import com.detyra.miniiotsystem.exception.ModelErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelErrorMessage> handleConstraintViolationException(Exception ex, HttpServletRequest request){
        var resp = ModelErrorMessage.builder()
                .message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }
}
