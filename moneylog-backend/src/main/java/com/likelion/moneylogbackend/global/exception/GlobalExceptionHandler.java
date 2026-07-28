package com.likelion.moneylogbackend.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnumValue(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("유효하지 않은 Enum 값입니다.");
    }
}
