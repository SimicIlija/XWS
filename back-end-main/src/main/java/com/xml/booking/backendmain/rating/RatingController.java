package com.xml.booking.backendmain.rating;


import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


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
}
