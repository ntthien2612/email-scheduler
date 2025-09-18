package com.example.emailscheduler.exception;


public class TemplateInUseException extends RuntimeException {
    public TemplateInUseException(String message) {
        super(message);
    }
}
