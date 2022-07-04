package cz.majzlik.assignment.reservations.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;

/**
 * @author Adam Majzlik
 */
@Converter
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalTime time) {
        return time.toString();
    }

    @Override
    public LocalTime convertToEntityAttribute(String time) {
        String[] numbers = time.split(":");
        int hours = Integer.parseInt(numbers[0]);
        int minutes = Integer.parseInt(numbers[1]);
        return LocalTime.of(hours, minutes);
    }

    /**
     * Converts string time to LocalTime object
     *
     * @param time to be converted
     * @return LocalTime object
     */
    public LocalTime convertToLocalTime(String time) {
        return this.convertToEntityAttribute(time);
    }
}
