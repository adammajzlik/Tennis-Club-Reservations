package cz.majzlik.assignment.reservations.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Adam Majzlik
 */
@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Override
    public LocalDate convertToEntityAttribute(String date) {
        String[] numbers = date.split("\\.");
        int day = Integer.parseInt(numbers[0]);
        int month = Integer.parseInt(numbers[1]);
        int year = Integer.parseInt(numbers[2]);
        return LocalDate.of(year, month, day);
    }

    /**
     * Converts date to LocalDate object
     *
     * @param date to be converted
     * @return LocalDate object
     */
    public LocalDate convertToLocalDate(String date) {
        return this.convertToEntityAttribute(date);
    }
}
