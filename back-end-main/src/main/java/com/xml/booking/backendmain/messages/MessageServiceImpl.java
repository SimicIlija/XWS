package com.xml.booking.backendmain.messages;

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
		if(id == null)
			return null;
		return messageRepository.getOne(id);
	}
	
	
	@Override
	public List<Message> getMessages(Long userId) {
		return messageRepository.findBySender_IdAndMaster(userId, true);
	}

	@Override
	@Transactional(readOnly = false)
	public Message addMessage(Long masterId, Message input) {
		if(masterId == -1L) {
			input = messageRepository.save(input);
		} else {
			Message master = messageRepository.getOne(masterId);
			if(master == null || !master.getMaster())
				return null;
			input = messageRepository.save(input);
			master.getMessages().add(input);
			messageRepository.save(master);
		}
		
		return input;
	}


	@Override
	public List<Message> findAll() {
		return messageRepository.findAll();
	}


	@Override
	public List<Message> getMasters() {
		return messageRepository.findByMaster(true);
	}
	
}
