package com.xml.booking.agent.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class User {
	
	@Id
	@GeneratedValue
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
	
	@NotNull
	private long workId;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	@Size(min = 6)
	private String password;

	public User() {}

	public User(String firstName, String lastName, String address, long workId, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.workId = workId;
		this.email = email;
		this.password = password;
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
