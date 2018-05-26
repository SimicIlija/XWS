package com.xml.booking.backendmain.reservations;

import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    private final HttpSession session;

    @Autowired
    public ReservationController(ReservationService reservationService, HttpSession session) {
        this.reservationService = reservationService;
        this.session = session;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Reservation> allReservations = reservationService.findAll();
        return new ResponseEntity<>(allReservations, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewReservation(@Valid @RequestBody ReservationDto reservationDto) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }

        Reservation reservation = reservationService.addNew(reservationDto, user);
        return new ResponseEntity<Object>(reservation, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@PathVariable long id) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }
        reservationService.cancelResrvation(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
