package elevators.infrastructure;

import elevators.domain.exceptions.IncorrectFloorNumberException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class FloorExceptionHandler {

    @ExceptionHandler(IncorrectFloorNumberException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FloorErrorResponse handleOfferNotFoundException(IncorrectFloorNumberException exception) {
        String message = exception.getMessage();
        log.error(message);
        return new FloorErrorResponse(message, HttpStatus.NOT_FOUND);
    }
}
