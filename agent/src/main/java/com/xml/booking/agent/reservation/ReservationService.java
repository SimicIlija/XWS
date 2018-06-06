package com.xml.booking.agent.reservation;

import java.util.List;

public interface ReservationService {
	
	public Reservation findOne(Long id);
	
	public List<Reservation> findAll();
	
	public List<Reservation> findUnavailableDates(Long accommodationId);
	
	public Reservation addReservation(Reservation input);
	
	public List<Reservation> addReservations(List<Reservation> inputs);
	
	public Reservation confirmReservation(Long id);
	
	public Reservation deleteReservation(Long id);

}
