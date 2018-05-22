package com.xml.booking.backendmain.comment;

import java.util.List;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.users.User;

public interface CommentService {

	Comment findOne(Long id);
	
	List<Comment> findAll();
	
	List<Comment> findByUser(User user);
	
	List<Comment> findByLodging(Lodging lodging);
	
	Comment add(Comment comment);
	
	Comment edit(Comment comment);
	
	Comment delete(Comment comment);
	
	Comment approve(Comment comment);
}
