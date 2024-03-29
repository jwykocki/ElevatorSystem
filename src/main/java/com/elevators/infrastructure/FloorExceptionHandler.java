package com.elevators.infrastructure;

import com.elevators.domain.exceptions.IncorrectFloorNumberException;
import com.elevators.domain.exceptions.IncorrectPickupRequestFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
class FloorExceptionHandler {

    @ExceptionHandler(IncorrectFloorNumberException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FloorErrorResponse handleFloorNotFoundException(IncorrectFloorNumberException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new FloorErrorResponse(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPickupRequestFormat.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FloorErrorResponse handleIncorrectPickupRequestFormat(IncorrectPickupRequestFormat exception) {
        String message = exception.getMessage();
        log.error(message);
        return new FloorErrorResponse(message, HttpStatus.BAD_REQUEST);
    }
}
