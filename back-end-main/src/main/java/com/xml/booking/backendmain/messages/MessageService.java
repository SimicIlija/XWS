package com.xml.booking.backendmain.messages;

import java.util.List;

public interface MessageService {
	
	public List<Message> findAll();
	
	public Message findOne(Long id);
	
	public List<Message> getMessages(Long userId);
	
	public List<Message> getMasters();
	
	public Message addMessage(Long masterId, Message input);
	
}
