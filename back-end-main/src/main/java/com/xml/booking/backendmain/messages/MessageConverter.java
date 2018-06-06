package com.xml.booking.backendmain.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.reservations.ReservationService;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;

@Component
public class MessageConverter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReservationService reservationService;
	
	public Message fromDTO(User sender, MessageDTO dto) {
		if(dto.getContent().trim().isEmpty())
			return null;
		
		User receiver = userService.findOne(dto.getReceiver());
		if(receiver == null)
			return null;
		
		if(!reservationService.canSendMessage(sender.getId(), receiver.getId()))
			return null;
		
		Message ret = new Message(sender, receiver, System.currentTimeMillis(), dto.getContent(), false, null);
		if(dto.getMaster() == -1L)
			ret.setMaster(true);
		
		return ret;
	}
}
