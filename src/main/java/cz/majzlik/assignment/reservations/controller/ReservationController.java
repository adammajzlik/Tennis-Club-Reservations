package cz.majzlik.assignment.reservations.controller;

import cz.majzlik.assignment.reservations.exception.InvalidReservationException;
import cz.majzlik.assignment.reservations.model.Court;
import cz.majzlik.assignment.reservations.model.Customer;
import cz.majzlik.assignment.reservations.model.Reservation;
import cz.majzlik.assignment.reservations.repository.CourtRepository;
import cz.majzlik.assignment.reservations.repository.CustomerRepository;
import cz.majzlik.assignment.reservations.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Class that creates service end-points related to the reservation.
 *
 * @author Adam Majzlik
 */
@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Gets all reservations of courts in the club.
     *
     * @return list of all reservations of courts
     */
    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Gets all reservations related to the given court number.
     *
     * @param courtNumber reservations related to this court number are being searched for
     * @return list of all reservation related to the given court number
     */
    @GetMapping("/reservations/court/{courtNumber}")
    public List<Reservation> getReservationByCourtNumber(@PathVariable int courtNumber) {
        return reservationRepository.getReservationsByCourtNumber(courtNumber);
    }

    /**
     * Gets all reservations related to the given phone number of the customer.
     *
     * @param phoneNumber reservations are being searched for this phone number
     * @return list of all reservations related to the given phone number
     */
    @GetMapping("/reservations/phone/{phoneNumber}")
    public List<Reservation> getReservationByPhoneNumber(@PathVariable String phoneNumber) {
        return reservationRepository.getReservationsByPhoneNumber(phoneNumber);
    }

    /**
     * Creates new reservation. If customer is not saved in database, new customer is created and saved.
     *
     * @param reservation to be created
     * @return price for reservation
     */
    @PostMapping("/reservation/create")
    public double createReservation(@RequestBody Reservation reservation) {
        reservation.validate();

        String dateFormatted = reservation.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List<Reservation> reservations = reservationRepository.getReservationsByDateAndCourt(dateFormatted, reservation.getCourtNumber());

        reservation.checkReservationConditions();
        reservation.checkColliding(reservations);

        Customer checkCustomer = customerRepository.getCustomerByPhoneNumber(reservation.getPhoneNumber());
        if (Objects.isNull(checkCustomer)) {
            Customer newCustomer = new Customer(reservation.getPhoneNumber(), reservation.getName());
            customerRepository.save(newCustomer);
        } else if (!checkCustomer.getName().equals(reservation.getName())) {
            throw new InvalidReservationException("Customer related to this phone number already exists.");
        }

        reservationRepository.save(reservation);

        Court court = courtRepository.getCourtByCourtNumber(reservation.getCourtNumber());
        return reservation.countPrice(court);
    }
}
