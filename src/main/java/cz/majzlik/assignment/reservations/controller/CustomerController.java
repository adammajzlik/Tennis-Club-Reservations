package cz.majzlik.assignment.reservations.controller;

import cz.majzlik.assignment.reservations.model.Customer;
import cz.majzlik.assignment.reservations.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class that creates service end-points related to the customer.
 *
 * @author Adam Majzlik
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Gets all customers of the club.
     *
     * @return list of all customer of the club.
     */
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Creates new customer of the club.
     *
     * @param customer to be created
     * @return information message
     */
    @PostMapping("/customer/create")
    public String createCustomer(Customer customer) {
        customerRepository.save(customer);
        return "New customer was created.";
    }
}
