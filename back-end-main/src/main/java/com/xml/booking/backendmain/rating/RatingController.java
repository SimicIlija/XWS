package com.xml.booking.backendmain.rating;


import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.exceptions.NotFoundException;
import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingRepository;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserRepository;
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
    private final UserRepository userRepository;
    private final LodgingRepository lodgingRepository;
    private HttpSession session;

    @Autowired
    public RatingController(RatingService ratingService, UserRepository userRepository, LodgingRepository lodgingRepository, HttpSession session) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
        this.lodgingRepository = lodgingRepository;
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
        double value = ratingService.getAverageGrade(idLodging);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @RequestMapping(value = "/data/{idLodging}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLodgingData(@PathVariable long idLodging) {
        List<RatingDto> list = ratingService.getLodgingData(idLodging);
        for (RatingDto temp : list) {
            User user = userRepository.findById(temp.getIdUser()).orElseThrow(NotFoundException::new);
            temp.setUser(user);
        }
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
        for (RatingDto temp : list) {
            User u = userRepository.findById(temp.getIdUser()).orElseThrow(NotFoundException::new);
            Lodging l = lodgingRepository.findById(temp.getIdLodging()).orElseThrow(NotFoundException::new);
            temp.setUser(u);
            temp.setLodging(l);
        }
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

    @RequestMapping(value = "/check/{idReservation}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkComment(@PathVariable long idReservation) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AuthException("Login first");
        }
        RatingDto ratingDto = ratingService.findDto(idReservation, user);
        return new ResponseEntity<Object>(ratingDto, HttpStatus.OK);
    }
}
