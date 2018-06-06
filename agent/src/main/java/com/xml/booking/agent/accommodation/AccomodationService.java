package com.xml.booking.agent.accommodation;

import java.util.List;

public interface AccomodationService {

	Accommodation findOne(Long id);
	
	List<Accommodation> findAll();
	
	Accommodation add(Accommodation accommodation);
	
}
