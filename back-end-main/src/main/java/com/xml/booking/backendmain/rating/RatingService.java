package com.xml.booking.backendmain.rating;

import com.xml.booking.backendmain.exceptions.BadRequestException;
import com.xml.booking.backendmain.exceptions.NotFoundException;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class RatingService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public RatingService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean addNewRC(RatingDto ratingDto) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/addRateCom";
        Long reservationId = ratingDto.getIdReservation();
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found."));
        if (reservation.getEndDate().after(new Date())) {
            throw new BadRequestException("Reservation didn't ended, you can't make rating nor comment");
        }
        ratingDto.setIdLodging(reservation.getLodging().getId());
        ratingDto.setIdUser(reservation.getUser().getId());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<RatingDto> request = new HttpEntity<>(ratingDto);
        try {
            restTemplate.postForEntity(url, request, Object.class);
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }
}
