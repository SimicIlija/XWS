package com.xml.booking.backendmain.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
	
	@Autowired
	UserService userService;
	
	
	public User fromEditDTO(EditDTO dto, User user) {
		User userDTO = dto.getUser();
		String oldPassword = dto.getOldPassword();
		
		if(userDTO == null)
			return null;
		
		if(oldPassword != null && !user.getPassword().equals(oldPassword))
			return null;
		
		if(oldPassword == null && userDTO.getPassword() != null)
			userDTO.setPassword(null);
		
		
		
		return userDTO;
	}
	
}
