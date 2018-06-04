package com.xml.booking.agent.messages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Message findOne(Long id) {
		return messageRepository.findOne(id);
	}
	
	
	@Override
	public List<Message> getMessages(Long agentId) {
		return messageRepository.findByReceiver_IdAndMaster(agentId, true);
	}

	@Override
	@Transactional(readOnly = false)
	public Message addMessage(Long masterId, Message input) {
		Message master = messageRepository.findOne(masterId);
		if(master == null)
			return null;
		
		input.setReceiver(master.getSender());
		input = messageRepository.save(input);
		master.getMessages().add(input);
		messageRepository.save(master);
		
		return input;
	}
	
}
