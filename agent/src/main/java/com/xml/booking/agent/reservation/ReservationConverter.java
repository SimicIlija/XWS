package com.xml.booking.agent.reservation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.agent.accommodation.Accommodation;
import com.xml.booking.agent.accommodation.AccomodationService;

@Component
public class ReservationConverter {
	
	@Autowired
	private AccomodationService accomodationService;
	
	public List<Reservation> fromDTO(UnavailableDatesDTO dto) {
		
		Accommodation acc = accomodationService.findOne(dto.getAccId());
		if(acc == null)
			return null;
		
		ArrayList<Reservation> ret = new ArrayList<>();
		for (ArrayList<Long> fromTo : dto.getDates()) {
			if(fromTo.size() != 2 || fromTo.get(0) == null || fromTo.get(1) == null || fromTo.get(0) > fromTo.get(1))
				return null;
			
			ret.add(new Reservation(acc, null, fromTo.get(0), fromTo.get(1)));
		}
		
		return ret;
	}
	
}
