package cz.majzlik.assignment.reservations.repository;

import cz.majzlik.assignment.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface that provides methods for manipulating with reservation database.
 *
 * @author Adam Majzlik
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Gets all reservations for given court number from database
     *
     * @param courtNumber by this court number are reservations being searched for
     * @return list of all reservations related to the given court number
     */
    @Query(value = "SELECT * FROM reservation r WHERE r.court_number = ?1", nativeQuery = true)
    List<Reservation> getReservationsByCourtNumber(Integer courtNumber);

    /**
     * Gets all reservations for given phone number from database
     *
     * @param phoneNumber by this phone number are reservations being searched for
     * @return list of all reservations related to the given phone number
     */
    @Query(value = "SELECT * FROM reservation r WHERE r.phone_number = ?1", nativeQuery = true)
    List<Reservation> getReservationsByPhoneNumber(String phoneNumber);

    /**
     * Gets all reservations for given date and number from database
     *
     * @param date by this date are reservations being searched for
     * @param number by this court number are reservations being searched for
     * @return list of all reservations related to the given date and court number
     */
    @Query(value = "SELECT * FROM reservation r WHERE r.date = ?1 AND r.court_number = ?2", nativeQuery = true)
    List<Reservation> getReservationsByDateAndCourt(String date, Integer number);
}
