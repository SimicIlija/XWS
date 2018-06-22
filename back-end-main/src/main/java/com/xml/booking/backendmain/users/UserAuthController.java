package com.xml.booking.backendmain.users;

import java.net.UnknownHostException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/api/user-auth")
public class UserAuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private HttpSession session;


	@PostMapping
	public ResponseEntity<User> register(@RequestBody @Valid User user) 
			throws  InterruptedException, UnknownHostException {
		user.setUserType(UserType.VISITOR);
		User registered = userService.register(user);
		if(registered == null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		return new ResponseEntity<>(registered, HttpStatus.OK);
	}
	
	@PostMapping("/agent")
	public ResponseEntity<User> registerAgent(@RequestBody @Valid User user) 
			throws  InterruptedException, UnknownHostException {
		User logedIn = (User) session.getAttribute("user");
		if(logedIn == null || !logedIn.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		user.setUserType(UserType.AGENT);
		User registered = userService.register(user);
		if(registered == null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		
		return new ResponseEntity<>(registered, HttpStatus.OK);
	}
	
	@GetMapping("/confirm/{token}")
	public ResponseEntity<User> confirm(@PathVariable String token) {
		User user = userService.confirm(token);
		if(user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<User> logIn(@RequestBody @Valid User user) {
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

	@GetMapping
	public ResponseEntity<User> getUser() {
		User user = (User) session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/edit")
	public ResponseEntity<User> editUser(@RequestBody EditDTO input) {
		User sessionUser = (User) session.getAttribute("user");
		if(sessionUser == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		User user = userConverter.fromEditDTO(input, sessionUser);
		if(user == null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		User newUser = userService.edit(sessionUser.getId(), user);
		if(newUser == null)
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		session.setAttribute("user", newUser);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	

	
}
