package cz.majzlik.assignment.reservations.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.majzlik.assignment.reservations.converter.LocalDateConverter;
import cz.majzlik.assignment.reservations.converter.LocalTimeConverter;
import cz.majzlik.assignment.reservations.deserializer.ReservationDateDeserializer;
import cz.majzlik.assignment.reservations.deserializer.ReservationGameTypeDeserializer;
import cz.majzlik.assignment.reservations.deserializer.ReservationTimeDeserializer;
import cz.majzlik.assignment.reservations.exception.InvalidReservationException;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Class that represents Reservations of the tennis court.
 *
 * @author Adam Majzlik
 */
@Entity
@Table
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonDeserialize(using = ReservationDateDeserializer.class)
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = ReservationTimeDeserializer.class)
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime time;

    @JsonDeserialize(using = ReservationGameTypeDeserializer.class)
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private double hours;

    private String phoneNumber;

    private int courtNumber;

    private String name;

    private static final String PHONE_NUMBER_FORMAT = "\\+[0-9]+|[0-9]+";

    /**
     * Validates format of phone number and name.
     */
    public void validate() {
        if (!phoneNumber.matches(PHONE_NUMBER_FORMAT)) {
            throw new InvalidReservationException("Phone number has an invalid format.");
        }
        if (name.isEmpty()) {
            throw new InvalidReservationException("Name must be filled.");
        }
    }

    /**
     * Checks if reservations is colliding with other reservations.
     *
     * @param reservations these reservations is new reservation compared with
     */
    public void checkColliding(List<Reservation> reservations) {
        boolean collide = false;
        for (Reservation reservation : reservations) {
            long newMinutes = (long) (this.hours * 60);
            long oldMinutes = (long) (reservation.getHours() * 60);

            LocalTime newStartTime = this.time;
            LocalTime newEndTime = newStartTime.plusMinutes(newMinutes);

            LocalTime oldStartTime = reservation.getTime();
            LocalTime oldEndTime = oldStartTime.plusMinutes(oldMinutes);

            if (newStartTime.equals(oldStartTime) || newEndTime.equals(oldEndTime)) {
                collide = true;
            } else if (newStartTime.isAfter(oldStartTime) && newStartTime.isBefore(oldEndTime)) {
                collide = true;
            } else if (newEndTime.isAfter(oldStartTime) && newEndTime.isBefore(oldEndTime)) {
                collide = true;
            } else if (newStartTime.isBefore(oldStartTime) && newEndTime.isAfter(oldEndTime)) {
                collide = true;
            }

            if (collide) {
                throw new InvalidReservationException("Reservation collides with another reservation.");
            }
        }
    }

    /**
     * Checks if new reservation fulfills club reservation conditions.
     */
    public void checkReservationConditions() {
        LocalTime endTime = this.time.plusMinutes((long) (hours * 60));
        if (endTime.isAfter(Club.getClosingTime()) || endTime.isBefore(Club.getOpeningTime())) {
            throw new InvalidReservationException("End time is after closing time of the club.");
        }
        if (this.time.isBefore(Club.getOpeningTime())) {
            throw new InvalidReservationException("Start time is before opening time of the club.");
        }
        if (this.hours > 4) {
            throw new InvalidReservationException("Reservation can not be longer than 4 hours.");
        }
        if (this.hours <= 0) {
            throw new InvalidReservationException("Reservation can not be created for 0 or less hours.");
        }
        if (this.hours % 0.5 != 0) {
            throw new InvalidReservationException("Reservation can must be in half a hour intervals.");
        }
        if (this.courtNumber < 1 || this.courtNumber > Club.getNumOfCourts()) {
            throw new InvalidReservationException("Reservation has an invalid court number.");
        }
        if (this.date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("Reservation can not be created for the past.");
        }
        if (this.date.equals(LocalDate.now()) && this.time.isBefore(LocalTime.now())) {
            throw new InvalidReservationException("Reservation can not be created for the past.");
        }
    }

    /**
     * Counts price for reservation.
     *
     * @param court price of reservation of this court
     * @return price for reservation
     */
    public double countPrice(Court court) {
        double minutes = this.getHours() * 60;
        double surfacePrice = court.getSurface().getPrice();
        GameType gameType = this.getGameType();
        double price = minutes * surfacePrice;
        if (gameType == GameType.DOUBLES) {
            price *= 1.5;
        }
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(int courtNumber) {
        this.courtNumber = courtNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
