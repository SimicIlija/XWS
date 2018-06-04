package com.xml.booking.agent.messages;

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
@RequestMapping("/rest/message")
public class MessageController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageConverter messageConverter;
	
	@GetMapping("/{id:\\d+}")
	public ResponseEntity<Message> getMessage(@PathVariable Long id) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Message ret = messageService.findOne(id);
		if(ret == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Message>> getMessages() {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Message> ret = messageService.getMessages(user.getId());
		if(ret == null || ret.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<Message> sendMessage(@RequestBody @Valid MessageDTO msg) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Message message = messageConverter.fromDTO(user, msg);
		if(message == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		message = messageService.addMessage(msg.getMaster(), message);
		if(message == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
}
