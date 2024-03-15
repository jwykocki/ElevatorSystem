package elevators.infrastructure;

import org.springframework.http.HttpStatus;

public record FloorErrorResponse(String message, HttpStatus status) {

    public static FloorErrorResponse of(String message, HttpStatus status) {
        return new FloorErrorResponse(message, status);
    }
}
