package cz.majzlik.assignment.reservations.repository;

import cz.majzlik.assignment.reservations.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface that provides methods for manipulating with customer database.
 *
 * @author Adam Majzlik
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Gets customer from database by given phone number
     *
     * @param phoneNumber by this phone number the customer is being found
     * @return customer with given phone number and name
     */
    @Query(value = "SELECT * FROM customer c WHERE c.phone_number = ?1", nativeQuery = true)
    Customer getCustomerByPhoneNumber(String phoneNumber);
}
