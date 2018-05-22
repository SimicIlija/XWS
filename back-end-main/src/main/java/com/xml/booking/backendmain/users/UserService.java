package com.xml.booking.backendmain.users;

import java.util.List;

public interface UserService {

	User findOne(Long id);

	List<User> findAll();

	User register(User user);

	User confirm(String token); 

	User logIn(User user);

	User edit(Long id, User user);
	
	User delete(Long id);

	User block_unblock(Long id);
}
