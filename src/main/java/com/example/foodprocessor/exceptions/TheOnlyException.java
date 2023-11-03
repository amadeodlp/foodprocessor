package com.example.foodprocessor.exceptions;

public class TheOnlyException extends Exception {
    
    public TheOnlyException() {
        super("There has been an error. You have to stand very, very still! DON'T MOVE!");
    }
    
    public TheOnlyException(String message) {
        super(message);
    }
    
    public TheOnlyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TheOnlyException(Throwable cause) {
        super(cause);
    }
}