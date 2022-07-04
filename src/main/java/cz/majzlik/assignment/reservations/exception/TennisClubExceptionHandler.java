package cz.majzlik.assignment.reservations.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Class that implements handlers of exceptions.
 *
 * @author Adam Majzlik
 */
@ControllerAdvice
public class TennisClubExceptionHandler {

    /**
     * Handles InvalidReservationException
     *
     * @param ex exception which is being handled
     * @return response
     */
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Object> handleInvalidReservationException(InvalidReservationException ex) {
        TennisClubApiError tennisClubApiError = new TennisClubApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(tennisClubApiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles HttpMessageNotReadableException and JsonParseException
     *
     * @param ex exception which is being handled
     * @return response
     */
    @ExceptionHandler(value = { HttpMessageNotReadableException.class, JsonParseException.class})
    public ResponseEntity<Object> handleMessageNotReadableException(InvalidReservationException ex) {
        TennisClubApiError tennisClubApiError = new TennisClubApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(tennisClubApiError, HttpStatus.BAD_REQUEST);
    }
}
