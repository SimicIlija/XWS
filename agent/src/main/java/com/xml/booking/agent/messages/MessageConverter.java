package com.xml.booking.agent.messages;

import org.springframework.stereotype.Component;

import com.xml.booking.agent.user.User;

@Component
public class MessageConverter {
	
	public Message fromDTO(User sender, MessageDTO dto) {
		if(dto.getContent().trim().isEmpty())
			return null;
		
		return new Message(sender, null, System.currentTimeMillis(), dto.getContent(), false, null);
	}
}
