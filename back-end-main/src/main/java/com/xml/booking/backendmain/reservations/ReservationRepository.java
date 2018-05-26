package com.xml.booking.backendmain.reservations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public List<Reservation> findByLodging_Id(long id);

    public List<Reservation> findByUser_Id(long id);
}
