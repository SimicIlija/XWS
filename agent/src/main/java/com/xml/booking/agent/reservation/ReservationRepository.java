package com.xml.booking.agent.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xml.booking.agent.user.User;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	public List<Reservation> findByAccommodation_Id(Long id);
	
    public List<Reservation> findByAccommodation_IdAndUser(Long id, User user);
    
    public List<Reservation> findByUser_Id(Long id);
    
    public List<Reservation> findByConfirmedAndUserNotNull(Boolean confirmed);
}
