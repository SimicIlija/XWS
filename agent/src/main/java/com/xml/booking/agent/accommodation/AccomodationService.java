package com.xml.booking.agent.accommodation;

import java.util.List;

import backendmain.wsdl.AccommodationXML;

public interface AccomodationService {

	Accommodation findOne(Long id);
	
	List<Accommodation> findAll();
	
	Accommodation add(Accommodation accommodation);
	
	void cloneDB(List<AccommodationXML> accommodationsXML);
	
}
