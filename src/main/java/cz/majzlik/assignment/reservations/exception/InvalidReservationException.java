package cz.majzlik.assignment.reservations.exception;

/**
 * Class that represents exception for invalid reservation.
 *
 * @author Adam Majzlik
 */
public class InvalidReservationException extends RuntimeException {

    public InvalidReservationException(String message) {
        super(message);
    }
}
