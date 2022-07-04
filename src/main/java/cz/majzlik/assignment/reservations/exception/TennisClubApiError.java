package cz.majzlik.assignment.reservations.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Class that represents error object
 *
 * @author Adam Majzlik
 */
public class TennisClubApiError {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;

    public TennisClubApiError(String message, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
