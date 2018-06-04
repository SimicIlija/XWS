package com.xml.booking.agent.user;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class UserLogIn {
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(min = 6)
	private String password;

	public UserLogIn() {}

	public UserLogIn(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
