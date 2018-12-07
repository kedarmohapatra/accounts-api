package com.exercise.doj.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static java.util.Collections.singletonMap;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${account.label.message}")
    private String message;

    @Value("${account.invalid.request}")
    private String invalidRequest;

    @Value("${account.invalid.id}")
    private String invalidId;

    @ExceptionHandler({ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, singletonMap(message, invalidRequest),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleInvalidIdRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, singletonMap(message, invalidId),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}