package com.xml.booking.agent.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Override
	public Reservation findOne(Long id) {
		return reservationRepository.findOne(id);
	}

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public List<Reservation> findUnavailableDates(Long accommodationId) {
		return reservationRepository.findByAccommodation_IdAndUser(accommodationId, null);
	}

	@Override
	@Transactional(readOnly = false)
	public Reservation addReservation(Reservation input) {
		return reservationRepository.save(input);
	}
	
	@Override
	@Transactional(readOnly = false)
	public List<Reservation> addReservations(List<Reservation> inputs) {
		return reservationRepository.save(inputs);
	}

	@Override
	@Transactional(readOnly = false)
	public Reservation confirmReservation(Long id) {
		Reservation dbRes = findOne(id);
		if(dbRes == null)
			return null;
		//dbRes.setConfirmed();
		return reservationRepository.save(dbRes);
	}

	@Override
	@Transactional(readOnly = false)
	public Reservation deleteReservation(Long id) {
		Reservation dbRes = findOne(id);
		if(dbRes == null)
			return null;
		
		reservationRepository.delete(id);
		return dbRes;
	}
	
}
