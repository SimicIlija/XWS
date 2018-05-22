package com.xml.booking.backendmain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.users.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByUser(User user);
	
	List<Comment> findByLodging(Lodging lodging);
}
