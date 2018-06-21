package com.xml.booking.backendmain.rating;

import com.xml.booking.backendmain.exceptions.BadRequestException;
import com.xml.booking.backendmain.exceptions.NotFoundException;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationRepository;
import com.xml.booking.backendmain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;

@Service
public class RatingService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public RatingService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean addNewRC(RatingDto ratingDto, User user) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/addRateCom";
        Long reservationId = ratingDto.getIdReservation();
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found."));
        if (reservation.getEndDate().after(new Date())) {
            throw new BadRequestException("Reservation didn't ended, you can't make rating nor comment");
        }
        if (!Objects.equals(user.getId(), reservation.getUser().getId())) {
            throw new BadRequestException("Reservation user error");
        }
        ratingDto.setIdUser(reservation.getUser().getId());
        ratingDto.setIdLodging(reservation.getLodging().getId());
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
