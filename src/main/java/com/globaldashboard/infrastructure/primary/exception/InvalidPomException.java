package com.globaldashboard.infrastructure.primary.exception;

public class InvalidPomException extends RuntimeException{
    public InvalidPomException(Exception e) {
        super(e);
    }
}
