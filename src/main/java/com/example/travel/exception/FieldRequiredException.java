package com.example.travel.exception;

public class FieldRequiredException extends RuntimeException{

    public FieldRequiredException(String message) {
        super(message);
    }

}
