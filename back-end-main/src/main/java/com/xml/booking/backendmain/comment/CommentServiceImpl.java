package com.xml.booking.backendmain.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingService;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;

@Transactional(readOnly = true)
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired 
	private CommentRepository commentRepository;
	@Autowired
	private LodgingService lodgingService;
	@Autowired
	private UserService userService;

	@Override
	public Comment findOne(Long id) {
		try {
			return commentRepository.findById(id).get();
		
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public List<Comment> findByUser(User user) {
		return commentRepository.findByUser(user);
	}
	
	@Override
	public List<Comment> findByLodging(Lodging lodging) {
		return commentRepository.findByLodging(lodging);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Comment add(Comment comment) {
		comment.setLodging(lodgingService.findOne(comment.getLodging().getId()));
		return commentRepository.save(comment);
	}

	@Override
	@Transactional(readOnly = false)
	public Comment edit(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Comment delete(Comment comment) {
		Comment del = findOne(comment.getId());
		if(del == null)
			return null;
		try {
			commentRepository.delete(comment);
		}catch(Exception e) {
			System.out.println("Cant delete comment");
			return null;
		}
		return comment;
	}

	@Override
	@Transactional(readOnly = false)
	public Comment approve(Comment comment) {
		Comment app = findOne(comment.getId());
		if(app == null)
			return null;
		app.setApproved(true);
		return app;
	}


	
	
}
