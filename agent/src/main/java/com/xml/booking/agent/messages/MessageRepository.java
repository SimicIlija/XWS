package com.xml.booking.agent.messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	public List<Message> findByReceiver_IdAndMaster(Long id, Boolean master);
}
