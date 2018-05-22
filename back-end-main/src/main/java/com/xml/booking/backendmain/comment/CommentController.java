package com.xml.booking.backendmain.comment;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingService;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserType;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private LodgingService lodgingService;
	@Autowired
	private HttpSession session;
	
	@GetMapping
	public ResponseEntity<List<Comment>> getComments(){
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Comment> comments = commentService.findAll();
		if(comments == null || comments.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(comments,HttpStatus.OK);
		
	}
	
	@GetMapping("/lodging/{id:\\d+}")
	public ResponseEntity<List<Comment>> getCommentsByLodging(@PathVariable Long id){
		Lodging lodging = lodgingService.findOne(id);
		List<Comment> comments = commentService.findByLodging(lodging);
		if(comments == null || comments.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(comments,HttpStatus.OK);
		
	}
	
	@GetMapping("/notApproved")
	public ResponseEntity<List<Comment>> getCommentsNotApproved(){
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Comment> comments = commentService.findAll();
		List<Comment> ret = new ArrayList<>();
		for(Comment comment:comments) {
			if(!comment.isApproved())
				ret.add(comment);
		}
		if(ret == null || ret.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Comment> add(@RequestBody @Valid Comment input)
	{
		User user = (User) session.getAttribute("user");
		if(user == null || !(user.getId() == (input.getUser().getId()))  )
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		input.setLodging(lodgingService.findOne(input.getId()));
		input.setUser(user);
		
		Comment ret = commentService.add(input);
		if(ret == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id:\\d+}")
	public ResponseEntity<Comment> delete (@PathVariable Long id)
	{
		User user = (User) session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		Comment comment = commentService.findOne(id);
		if(user.getId() == comment.getUser().getId())
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Comment ret = commentService.delete(comment);
		if(ret == null)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(ret,HttpStatus.OK);
	}
	
	@PutMapping("/approve/{id:\\d+}")
	public ResponseEntity<Comment> approve(@PathVariable Long id){
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		
		Comment comment = commentService.findOne(id);
		comment = commentService.approve(comment);		
		
		return new ResponseEntity<>(comment,HttpStatus.OK);
	}
}
