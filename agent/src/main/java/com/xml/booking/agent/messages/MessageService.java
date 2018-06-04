package com.xml.booking.agent.messages;

import java.util.List;

public interface MessageService {
	
	public Message findOne(Long id);
	
	public List<Message> getMessages(Long agentId);
	
	public Message addMessage(Long masterId, Message input);
	
}
