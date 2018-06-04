package com.xml.booking.agent.user;

import java.util.List;

public interface UserService {
	
	User findOne(Long id);
	
	User findOne(String email);
	
	void updateDatabase(List<User> users);
	
	User logIn(UserLogIn user);
	
}
