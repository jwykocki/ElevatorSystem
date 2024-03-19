package com.elevators.infrastructure;

import org.springframework.http.HttpStatus;

record FloorErrorResponse(String message, HttpStatus status) {

    public static FloorErrorResponse of(String message, HttpStatus status) {
        return new FloorErrorResponse(message, status);
    }
}
