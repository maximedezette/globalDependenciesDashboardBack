package com.globaldashboard.infrastructure.primary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidPomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPomFileException(Exception ex) {
        return "Can not extract dependencies from pom file : " + ex.getMessage();
    }
}
