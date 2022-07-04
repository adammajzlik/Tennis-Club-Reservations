package cz.majzlik.assignment.reservations.repository;

import cz.majzlik.assignment.reservations.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface that provides methods for manipulating with court database.
 *
 * @author Adam Majzlik
 */
public interface CourtRepository extends JpaRepository<Court, Long> {

    /**
     * Gets court by given number
     *
     * @param number of the court which is being searched for
     * @return court by given court number
     */
    @Query(value = "SELECT * FROM court c WHERE c.number = ?1", nativeQuery = true)
    Court getCourtByCourtNumber(Integer number);
}
