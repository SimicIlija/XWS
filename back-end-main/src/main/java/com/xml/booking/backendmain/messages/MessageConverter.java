package com.xml.booking.backendmain.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationService;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;

@Component
public class MessageConverter {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MessageService messageService;
	
	public Message fromDTO(User sender, MessageDTO dto) {
		if(dto.getContent().trim().isEmpty())
			return null;
		
		User receiver = null;
		if(dto.getReceiver() > -1L) {
			receiver = userService.findOne(dto.getReceiver());
			if(receiver == null)
				return null;
			if(!reservationService.canSendMessage(sender.getId(), receiver.getId()))
				return null;
		}
		
		Reservation reservation = null;
		if(dto.getReservation() > -1L) {
			reservation = reservationService.finOne(dto.getReservation());
			if(reservation == null)
				return null;
			Message master = messageService.findOneReservation(dto.getReservation());
			if(master != null) {
				dto.setMaster(master.getId());
				receiver = master.getReceiver();
			} else {
				receiver = reservation.getLodging().getAgent();
			}
		}
		
		Message ret = new Message(sender, receiver, System.currentTimeMillis(), dto.getContent(), false, null);
		if(dto.getMaster() == -1L) {
			ret.setMaster(true);
			ret.setReservation(reservation);
		} else {
			Message master = messageService.findOne(dto.getMaster());
			if(master == null)
				return null;
			ret.setReceiver(master.getReceiver());
		}
		
		return ret;
	}
}
