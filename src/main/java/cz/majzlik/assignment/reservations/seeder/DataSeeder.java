package cz.majzlik.assignment.reservations.seeder;

import cz.majzlik.assignment.reservations.model.*;
import cz.majzlik.assignment.reservations.repository.CourtRepository;
import cz.majzlik.assignment.reservations.repository.CustomerRepository;
import cz.majzlik.assignment.reservations.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CourtRepository courtRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    public DataSeeder(CourtRepository courtRepository,
                      CustomerRepository customerRepository,
                      ReservationRepository reservationRepository) {
        this.courtRepository = courtRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // 1. Seed Courts - Only if the court table is completely empty
        if (courtRepository.count() == 0) {
            System.out.println("Court table empty. Seeding initial courts...");
            Court court1 = new Court();
            court1.setNumber(1);
            court1.setSurface(Surface.CLAY);

            Court court2 = new Court();
            court2.setNumber(2);
            court2.setSurface(Surface.GRASS);

            courtRepository.saveAll(Arrays.asList(court1, court2));
        } else {
            System.out.println("Courts already exist in database. Skipping...");
        }

        // 2. Seed Customers - Only if no customers exist
        if (customerRepository.count() == 0) {
            System.out.println("Customer table empty. Seeding initial customers...");
            Customer c1 = new Customer("+420123456789", "Jan Novak");
            Customer c2 = new Customer("+420987654321", "Adam Majzlik");

            customerRepository.saveAll(Arrays.asList(c1, c2));
        } else {
            System.out.println("Customers already exist in database. Skipping...");
        }

        // 3. Seed Reservations - Only if no reservations exist
        if (reservationRepository.count() == 0) {
            System.out.println("Reservation table empty. Seeding initial reservation...");
            Reservation res = new Reservation();
            res.setName("Jan Novak");
            res.setPhoneNumber("+420123456789");
            res.setCourtNumber(1);
            res.setDate(LocalDate.now().plusDays(1));
            res.setTime(LocalTime.of(10, 0));
            res.setHours(1.5);
            res.setGameType(GameType.SINGLES);

            reservationRepository.save(res);
        } else {
            System.out.println("Reservations already exist in database. Skipping...");
        }
    }
}