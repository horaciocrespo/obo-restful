package com.obo.oborestfulapp.controllers;

import com.obo.oborestfulapp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, "Resource not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//        return new ResponseEntity<>("Resource not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
