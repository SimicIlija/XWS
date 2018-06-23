package com.xml.booking.backendmain.messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	public List<Message> findBySender_IdAndMaster(Long id, Boolean master);
	
	public List<Message> findByMaster(Boolean master);

	public Message findByReservation_IdAndMaster(Long reservationId, boolean master);
	
}
