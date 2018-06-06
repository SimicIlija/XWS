package com.xml.booking.agent.accommodation;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.booking.agent.user.User;


@RestController
@RequestMapping("/rest/accommodation")
public class AccommodationController {
	
	@Autowired
	private AccomodationService accommodationService;
	@Autowired
	private AccommodationConverter accommodationConverter;
	@Autowired
	private HttpSession session;
	
	@GetMapping
	public ResponseEntity<List<Accommodation>> getAccommodations() {
//		User user = (User)session.getAttribute("user");
//		if(user == null)
//			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		List<Accommodation> accommodations = accommodationService.findAll();
		if(accommodations == null || accommodations.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(accommodations, HttpStatus.OK);
	}
	
	@GetMapping("/{id:\\d+}")
	public ResponseEntity<Accommodation> getAccommodation(@PathVariable Long id) {
//		User user = (User)session.getAttribute("user");
//		if(user == null)
//			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Accommodation accommodation = accommodationService.findOne(id);
		if(accommodation == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(accommodation, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Accommodation> addAccommodation(@RequestBody @Valid AccommodationDTO accommodationDTO) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		Accommodation accommodation = accommodationConverter.fromDTO(user, accommodationDTO);
		if(accommodation == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		accommodation = accommodationService.add(accommodation);
		if(accommodation == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(accommodation, HttpStatus.OK);
	}
	
}
