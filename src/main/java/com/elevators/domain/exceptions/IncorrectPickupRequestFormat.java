package com.elevators.domain.exceptions;


public class IncorrectPickupRequestFormat extends RuntimeException{
    public IncorrectPickupRequestFormat() {
        super("Incorrect pickup request format");
    }
}

