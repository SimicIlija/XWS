package com.xml.booking.backendmain.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addNew(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
