package com.xml.booking.backendmain.rating;


import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private HttpSession session;

    @Autowired
    public RatingController(RatingService ratingService, HttpSession session) {
        this.ratingService = ratingService;
        this.session = session;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addNewRC(@RequestBody RatingDto ratingDto) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }
        ratingService.addNewRC(ratingDto, user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/avg/{idLodging}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAverageGrade(@PathVariable long idLodging) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }
        double value = ratingService.getAverageGrade(idLodging);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @RequestMapping(value = "/data/{idLodging}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLodgingData(@PathVariable long idLodging) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }
        List<RatingDto> list = ratingService.getLodgingData(idLodging);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/notconfirmed",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotConfirmedComments() {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserType().equals(UserType.SYSADMIN)) {
            throw new AuthException("Only for admins");
        }
        List<RatingDto> list = ratingService.getNotConfirmedComments();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirm/{idReservation}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirmComment(@PathVariable long idReservation) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserType().equals(UserType.SYSADMIN)) {
            throw new AuthException("Only for admins");
        }
        boolean retVal = ratingService.confirmComment(idReservation);
        return retVal ? new ResponseEntity<Object>(HttpStatus.OK) : new ResponseEntity<Object>(HttpStatus.NOT_MODIFIED);
    }

}
