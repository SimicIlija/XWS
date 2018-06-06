package com.xml.booking.agent.reservation;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.booking.agent.user.User;

@RestController
@RequestMapping(value = "/rest/reservation")
public class ReservationController {
	@Autowired
	private HttpSession session;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ReservationConverter reservationConverter;
	
	
	@GetMapping("/unavailable/{id:\\d+}")
	public ResponseEntity<List<Reservation>> getUnavailableDates(@PathVariable Long id) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Reservation> ret = reservationService.findUnavailableDates(id);
		if(ret == null || ret.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@PostMapping("/unavailable")
	public ResponseEntity<List<Reservation>> setUnavailableDates(@RequestBody @Valid UnavailableDatesDTO dates) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Reservation> unavailableDates = reservationConverter.fromDTO(dates);
		if(unavailableDates == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		unavailableDates = reservationService.addReservations(unavailableDates);
		if(unavailableDates == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(unavailableDates, HttpStatus.OK);
	}
	
	@DeleteMapping("/unavailable/{id:\\d+}")
	public ResponseEntity<Reservation> deleteUnavailableDate(@PathVariable Long id) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Reservation res = reservationService.deleteReservation(id);
		if(res == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
