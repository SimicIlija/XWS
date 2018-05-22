package com.xml.booking.backendmain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.optionCatalog.CatalogService;
import com.xml.booking.backendmain.optionCatalog.OptionType;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.users.UserType;


@Component
public class TestData {

	@Autowired
	private UserService userService;
	@Autowired
	private CatalogService catalogService;
	
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

		User user7 = new User("sys@sys", "qweqwe", "Sheev", "Palpatine", "Naboo", null);
		user7.setUserType(UserType.SYSADMIN);
		user7.setEmailConfirmed(true);
		userService.register(user7);
		
		Catalog c11 = new Catalog(OptionType.TYPE, "Hotel");
		Catalog c12 = new Catalog(OptionType.TYPE, "bed&breakfast");
		Catalog c13 = new Catalog(OptionType.TYPE, "Apartman");
		catalogService.add(c11);
		catalogService.add(c12);
		catalogService.add(c13);
		
		Catalog c21 = new Catalog(OptionType.CATEGORY, "1 Star");
		Catalog c22 = new Catalog(OptionType.CATEGORY, "2 Star");
		Catalog c23 = new Catalog(OptionType.CATEGORY, "3 Star");
		Catalog c24 = new Catalog(OptionType.CATEGORY, "4 Star");
		Catalog c25 = new Catalog(OptionType.CATEGORY, "5 Star");
		Catalog c26 = new Catalog(OptionType.CATEGORY, "Unknown");
		catalogService.add(c21);
		catalogService.add(c22);
		catalogService.add(c23);
		catalogService.add(c24);
		catalogService.add(c25);
		catalogService.add(c26);
		
		
		
	}
}
