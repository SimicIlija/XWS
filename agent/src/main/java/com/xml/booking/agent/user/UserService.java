package com.xml.booking.agent.user;

import java.util.List;

import backendmain.wsdl.UserXML;

public interface UserService {
	
	User findOne(Long id);
	
	User findOne(String email);
	
	void cloneDB(List<UserXML> usersXML);
	
	User logIn(UserLogIn user);
	
}
