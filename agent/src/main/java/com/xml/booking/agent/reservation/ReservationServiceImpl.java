package com.xml.booking.agent.reservation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xml.booking.agent.accommodation.AccomodationService;
import com.xml.booking.agent.backendMain.BackendMainClient;
import com.xml.booking.agent.user.UserService;

import backendmain.wsdl.AddReservationsResponse;
import backendmain.wsdl.ConfirmReservationResponse;
import backendmain.wsdl.DeleteReservationResponse;
import backendmain.wsdl.ReservationXML;

@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private AccomodationService accomodationService;
	@Autowired
	private UserService userService;
	@Autowired
	private BackendMainClient backendMainClient;
	
	@Override
	public Reservation findOne(Long id) {
		return reservationRepository.findOne(id);
	}

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findByConfirmedAndUserNotNull(false);
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
		AddReservationsResponse response = backendMainClient.addReservations(inputs);
		inputs = new ArrayList<>();
		for(ReservationXML reservationXML : response.getReservations()) {
			Reservation tmp = new Reservation(reservationXML);
			if(reservationXML.getUser() != null)
				tmp.setUser(userService.findOne(reservationXML.getUser().getId()));
			tmp.setAccommodation(accomodationService.findOne(reservationXML.getAccommodation().getId()));
			inputs.add(tmp);
		}
		
		return reservationRepository.save(inputs);
	}

	@Override
	@Transactional(readOnly = false)
	public Reservation confirmReservation(Long id) {
		ConfirmReservationResponse response = backendMainClient.confirmReservation(id);
		if(response.getReservation() == null)
			return null;
		Reservation dbRes = findOne(id);
		if(dbRes == null)
			return null;
		dbRes.setConfirmed(true);
		return reservationRepository.save(dbRes);
	}

	@Override
	@Transactional(readOnly = false)
	public Reservation deleteReservation(Long id) {
		DeleteReservationResponse response = backendMainClient.deleteReservation(id);
		if(response.getReservation() == null)
			return null;
		Reservation dbRes = findOne(id);
		if(dbRes == null)
			return null;
		
		reservationRepository.delete(id);
		return dbRes;
	}

	@Override
	@Transactional(readOnly = false)
	public void cloneDB(List<ReservationXML> reservationsXML) {
		reservationRepository.deleteAllInBatch();
		for(ReservationXML reservationXML : reservationsXML) {
			Reservation tmp = new Reservation(reservationXML);
			tmp.setAccommodation(accomodationService.findOne(tmp.getAccommodation().getId()));
			if(reservationXML.getUser() != null)
				tmp.setUser(userService.findOne(tmp.getUser().getId()));
			reservationRepository.save(tmp);
		}
	}
	
}
