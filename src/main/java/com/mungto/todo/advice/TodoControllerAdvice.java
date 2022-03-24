package com.mungto.todo.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TodoControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(TodoControllerAdvice.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(Exception e) {
        logger.error("TodoServiceError", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 데이터입니다.");
    }
}
