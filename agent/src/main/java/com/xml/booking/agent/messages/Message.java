package com.xml.booking.agent.messages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.agent.user.User;

import backendmain.wsdl.MessageXML;

@Entity
public class Message {
	
	@Id
	//@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	
	@Version
	private long version;
	
	@NotNull
	@ManyToOne
	private User sender;
	
	@NotNull
	@ManyToOne
	private User receiver;
	
	@NotNull
	private Long timeStamp;
	
	@NotBlank
	private String content;
	
	@NotNull
	private Boolean master;
	
	@ManyToMany
	@OrderBy("timeStamp")
	private List<Message> messages;

	public Message() {}

	public Message(User sender, User receiver, Long timeStamp, String content, Boolean master, List<Message> messages) {
		this.sender = sender;
		this.receiver = receiver;
		this.timeStamp = timeStamp;
		this.content = content;
		this.master = master;
		this.messages = messages;
	}

	public Message(MessageXML messageXML) {
		this.id = messageXML.getId();
		if(messageXML.getSender() != null)
			this.sender = new User(messageXML.getSender());
		if(messageXML.getReceiver() != null)
			this.receiver = new User(messageXML.getReceiver());
		this.timeStamp = messageXML.getTimeStamp();
		this.content = messageXML.getContent();
		this.master = messageXML.isMaster();
		for(MessageXML messageXML2 : messageXML.getMessages())
			this.getMessages().add(new Message(messageXML2));
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getMaster() {
		return master;
	}

	public void setMaster(Boolean master) {
		this.master = master;
	}

	public List<Message> getMessages() {
		if(this.messages == null)
			this.messages = new ArrayList<>();
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
