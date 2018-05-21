package com.xml.booking.backendmain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.users.UserType;


@Component
public class TestData {

	@Autowired
	private UserService userService;
	
	@PostConstruct
	private void init() {
//		if(userService.findOne(1l) != null)
//			return;
		
		User user1 = new User("ch@me", "qweqwe", "Chewbacca", "Chewbacca", "Kashyyyk", null);
		user1.setUserType(UserType.VISITOR);
		user1.setEmailConfirmed(true);
		userService.register(user1);
		
		User user2 = new User("rms@me", "qweqwe", "Richard", "Stallman", "New York", null);
		user2.setUserType(UserType.VISITOR);
		user2.setEmailConfirmed(true);
		userService.register(user2);
		
		User user3 = new User("han@me", "qweqwe", "Han", "Solo", "Corellia", null);
		user3.setUserType(UserType.VISITOR);
		user3.setEmailConfirmed(true);
		userService.register(user3);
		
		User user4 = new User("pot@me", "qweqwe", "Lennart", "Poettering", "Guatemala City", null);
		user4.setUserType(UserType.VISITOR);
		user4.setEmailConfirmed(true);
		userService.register(user4);
		
		
		User user7 = new User("sys@sys", "qweqwe", "Sheev", "Palpatine", "Naboo", null);
		user7.setUserType(UserType.SYSADMIN);
		user7.setEmailConfirmed(true);
		userService.register(user7);
		
	}
}
