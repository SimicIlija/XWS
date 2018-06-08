package com.xml.booking.agent.messages;

import java.util.List;

import backendmain.wsdl.MessageXML;

public interface MessageService {
	
	public Message findOne(Long id);
	
	public List<Message> getMessages(Long agentId);
	
	public Message addMessage(Long masterId, Message input);
	
	void cloneDB(List<MessageXML> messagesXML);
	
}
