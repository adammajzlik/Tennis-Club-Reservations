package cz.majzlik.assignment.reservations.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import cz.majzlik.assignment.reservations.exception.InvalidReservationException;
import cz.majzlik.assignment.reservations.model.GameType;

import java.io.IOException;

/**
 * Class for deserializing game type in reservation.
 *
 * @author Adam Majzlik
 */
public class ReservationGameTypeDeserializer extends JsonDeserializer<GameType> {

    @Override
    public GameType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        String gameType = jsonParser.getText();
        if (gameType.equals(GameType.SINGLES.toString())) {
            return GameType.SINGLES;
        } else if (gameType.equals(GameType.DOUBLES.toString())) {
            return GameType.DOUBLES;
        } else {
            throw new InvalidReservationException("This game type does not exist.");
        }
    }
}
