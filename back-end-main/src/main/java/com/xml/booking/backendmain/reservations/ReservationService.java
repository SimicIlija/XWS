package com.xml.booking.backendmain.reservations;

import java.util.List;

import com.xml.booking.backendmain.users.User;


public interface ReservationService {
	
	public Reservation addNew(Reservation reservation);

    public List<Reservation> findAll();
 
    public Reservation addNew(ReservationDto reservationDto, User user);

    public void cancelResrvation(long id, User user);
    
    public Boolean canSendMessage(Long userId, Long agentId);
    
    public List<Reservation> findByUser(User user);
    
	public List<Reservation> addReservations(List<Reservation> inputs);

	public Reservation confirmReservation(Long id);
	
	public Reservation deleteReservation(Long id);
	
	public List<Reservation> findByUserHistory(User user);
	
	public List<Reservation> findByUserFuture(User user);

	Reservation finOne(Long id);
}
