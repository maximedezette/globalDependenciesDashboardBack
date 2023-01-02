package com.globaldashboard.dependencies.infrastructure.primary.exception;

public class InvalidPomException extends RuntimeException{
    public InvalidPomException(Exception e) {
        super(e);
    }
}
