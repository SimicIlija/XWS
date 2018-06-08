package com.xml.booking.agent.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xml.booking.agent.backendMain.BackendMainClient;
import com.xml.booking.agent.user.UserService;

import backendmain.wsdl.AddMessageResponse;
import backendmain.wsdl.DBRequestType;
import backendmain.wsdl.GetDBResponse;
import backendmain.wsdl.MessageXML;

@Transactional(readOnly = true)
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private BackendMainClient backendMainClient;
	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = false)
	public Message findOne(Long id) {
		GetDBResponse response = backendMainClient.getDB(Arrays.asList(DBRequestType.MESSAGE));
		cloneDB(response.getMessages());
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
		if(master == null || !master.getMaster())
			return null;
		input.setReceiver(master.getSender());
		
		AddMessageResponse response = backendMainClient.addMessage(masterId, input);
		if(response.getMessage() == null)
			return null;
		
		
		input = new Message(response.getMessage());
		input.setSender(userService.findOne(input.getSender().getId()));
		input.setReceiver(master.getSender());
		input = messageRepository.save(input);
		master.getMessages().add(input);
		messageRepository.save(master);
		
		return input;
	}


	@Override
	@Transactional(readOnly = false)
	public void cloneDB(List<MessageXML> messagesXML) {
		entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE MESSAGE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE MESSAGE_MESSAGES").executeUpdate();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
		for(MessageXML messageXML : messagesXML) {
			Message tmp = new Message(messageXML);
			tmp.setReceiver(userService.findOne(tmp.getReceiver().getId()));
			tmp.setSender(userService.findOne(tmp.getSender().getId()));
			ArrayList<Message> msgs = new ArrayList<>();
			for(Message message : tmp.getMessages()) {
				msgs.add(messageRepository.save(message));
			}
			tmp.setMessages(msgs);
			messageRepository.save(tmp);
		}
	}
}
