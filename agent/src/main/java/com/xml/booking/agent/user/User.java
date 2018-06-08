package com.xml.booking.agent.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.agent.user.UserType;

import backendmain.wsdl.UserXML;

@Entity
public class User {
	
	@Id
	//@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	
	@Version
	private long version;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String address;
	
	private long workId;
	
	@Enumerated(EnumType.STRING)
    @JsonProperty(access = Access.READ_ONLY)
	private UserType userType;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	@Size(min = 6)
	private String password;

	public User() {}

	public User(String firstName, String lastName, String address, long workId, String email, String password, UserType userType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.workId = workId;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	
	public User(UserXML userXML) {
		this.id = userXML.getId();
		this.firstName = userXML.getFirstName();
		this.lastName = userXML.getLastName();
		this.address = userXML.getAddress();
		this.workId = userXML.getWorkId();
		this.userType = UserType.valueOf(userXML.getUserType().toString());
		this.email = userXML.getEmail();
		this.password = userXML.getPassword();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
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
