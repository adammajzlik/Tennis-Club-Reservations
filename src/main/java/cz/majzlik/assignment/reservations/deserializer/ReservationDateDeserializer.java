package cz.majzlik.assignment.reservations.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import cz.majzlik.assignment.reservations.converter.LocalDateConverter;
import cz.majzlik.assignment.reservations.exception.InvalidReservationException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Class for deserializing date in reservation.
 *
 * @author Adam Majzlik
 */
public class ReservationDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        try {
            LocalDateConverter converter = new LocalDateConverter();
            return converter.convertToLocalDate(jsonParser.getText());
        } catch (Exception ex) {
            throw new InvalidReservationException("Date has an invalid format.");
        }
    }
}
