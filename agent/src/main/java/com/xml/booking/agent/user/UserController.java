package com.xml.booking.agent.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired 
	private HttpSession session;
	
	@GetMapping
	public ResponseEntity<User> getUser() {
		User user = (User) session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<User> logIn(@RequestBody @Valid UserLogIn user) {
		User newUser = userService.logIn(user);
		if(newUser == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		session.setAttribute("user", newUser);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<User> logOut() {
		User user = (User) session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		session.invalidate();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
}
