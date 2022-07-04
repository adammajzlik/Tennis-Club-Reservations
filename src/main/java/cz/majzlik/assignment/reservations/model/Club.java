package cz.majzlik.assignment.reservations.model;

import java.time.LocalTime;

/**
 * Class that represents the tennis club.
 *
 * @author Adam Majzlik
 */
public class Club {

    private static final LocalTime closingTime = LocalTime.of(23, 0);
    private static final LocalTime openingTime = LocalTime.of(7, 0);
    private static int numOfCourts = 0;

    public static LocalTime getClosingTime() {
        return closingTime;
    }

    public static int getNumOfCourts() {
        return numOfCourts;
    }

    public static LocalTime getOpeningTime() {
        return openingTime;
    }

    /**
     * Increases the number of courts in the club by 1.
     */
    public static void increaseNumberOfCourts() {
        Club.numOfCourts += 1;
    }
}
