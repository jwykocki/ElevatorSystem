package com.elevators.domain.exceptions;

public class IncorrectFloorNumberException extends RuntimeException{
    public IncorrectFloorNumberException() {
        super("Incorrect floor number");
    }
}
