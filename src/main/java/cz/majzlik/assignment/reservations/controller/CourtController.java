package cz.majzlik.assignment.reservations.controller;

import cz.majzlik.assignment.reservations.model.Club;
import cz.majzlik.assignment.reservations.model.Court;
import cz.majzlik.assignment.reservations.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class that creates service end-points related to the court.
 *
 * @author Adam Majzlik
 */

@RestController
@RequestMapping("/api")
public class CourtController {

    @Autowired
    private CourtRepository courtRepository;

    /**
     * Gets all courts in the club.
     *
     * @return list of all courts of the club.
     */
    @GetMapping("/courts")
    public List<Court> getCourts() {
        return courtRepository.findAll();
    }

    /**
     * Creates new court in the club.
     *
     * @param court to be created
     * @return information message
     */
    @PostMapping("/court/create")
    public String create(@RequestBody Court court) {
        courtRepository.save(court);
        Club.increaseNumberOfCourts();
        return "New court was created!";
    }
}
