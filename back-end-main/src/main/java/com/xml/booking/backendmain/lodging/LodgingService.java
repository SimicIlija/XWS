package com.xml.booking.backendmain.lodging;

import java.util.List;

public interface LodgingService {

	Lodging findOne(Long id);
	
	List<Lodging> findAll();
	
	Lodging add(Lodging lodging);
	
	Lodging delete(Lodging lodging);
}
