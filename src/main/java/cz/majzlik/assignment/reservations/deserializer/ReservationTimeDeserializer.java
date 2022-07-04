package cz.majzlik.assignment.reservations.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import cz.majzlik.assignment.reservations.converter.LocalTimeConverter;
import cz.majzlik.assignment.reservations.exception.InvalidReservationException;

import java.io.IOException;
import java.time.LocalTime;

/**
 * Class for deserializing time in reservation.
 *
 * @author Adam Majzlik
 */
public class ReservationTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        try {
            LocalTimeConverter converter = new LocalTimeConverter();
            return converter.convertToLocalTime(jsonParser.getText());
        } catch (Exception ex) {
            throw new InvalidReservationException("Time has an invalid format.");
        }
    }
}
