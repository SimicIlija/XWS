package com.xml.booking.backendmain.rating;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.exceptions.BadRequestException;
import com.xml.booking.backendmain.exceptions.NotFoundException;
import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingRepository;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationRepository;
import com.xml.booking.backendmain.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


@Service
public class RatingService {
    private final ReservationRepository reservationRepository;

    private final LodgingRepository lodgingRepository;

    @Autowired
    public RatingService(ReservationRepository reservationRepository, LodgingRepository lodgingRepository) {
        this.reservationRepository = reservationRepository;
        this.lodgingRepository = lodgingRepository;
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

    public double getAverageGrade(long idLodging) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/averageGrade";
        Lodging lodging = lodgingRepository.findById(idLodging)
                .orElseThrow(() -> new NotFoundException("Lodging not found"));
        RestTemplate restTemplate = new RestTemplate();
        RatingDto ratingDto = new RatingDto();
        ratingDto.setIdLodging(idLodging);
        HttpEntity<RatingDto> request = new HttpEntity<>(ratingDto);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            Iterator<JsonNode> iterator = root.elements();
            while (iterator.hasNext()) {
                JsonNode element = iterator.next();
                try {
                    return element.path("AVG(rating)").doubleValue();
                } catch (Exception e) {
                    return 0;
                }
            }
            return 0;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }


    public List<RatingDto> getLodgingData(long idLodging) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/getLodging";
        Lodging lodging = lodgingRepository.findById(idLodging)
                .orElseThrow(() -> new NotFoundException("Lodging not found"));
        RestTemplate restTemplate = new RestTemplate();
        RatingDto ratingDto = new RatingDto();
        ratingDto.setIdLodging(idLodging);
        HttpEntity<RatingDto> request = new HttpEntity<>(ratingDto);
        try {
            ResponseEntity<List<RatingDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<RatingDto>>() {
                    });
            List<RatingDto> ratings = response.getBody();
            return ratings;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }

    public List<RatingDto> getNotConfirmedComments() {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/unconfirmedComments";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<RatingDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RatingDto>>() {
                    });
            List<RatingDto> ratings = response.getBody();
            return ratings;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }

    public boolean confirmComment(long idReservation) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/acceptComment";
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        RatingDto ratingDto = new RatingDto();
        ratingDto.setIdLodging(reservation.getLodging().getId());
        ratingDto.setIdReservation(idReservation);
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

    public RatingDto findDto(long idReservation, User user) {
        Reservation reservation = reservationRepository.findById(idReservation).orElseThrow(NotFoundException::new);
        if(!Objects.equals(reservation.getUser().getId(), user.getId())){
            throw new AuthException("Bad user");
        }
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/testSql";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<RatingDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RatingDto>>() {
                    });
            List<RatingDto> ratings = response.getBody();
            for(RatingDto temp:ratings){
                if(temp.getIdReservation() == idReservation){
                    return temp;
                }
            }
            return null;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }

    public List<ResultLodging> filter(int rating) {
        String url = "https://us-central1-xml-cloud-206017.cloudfunctions.net/filterRating";
        RestTemplate restTemplate = new RestTemplate();
        FilterDto filterDto = new FilterDto();
        filterDto.setAvRate(rating);
        HttpEntity<FilterDto> request = new HttpEntity<>(filterDto);
        try {
            ResponseEntity<List<ResultLodging>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<ResultLodging>>() {
                    });
            List<ResultLodging> res = response.getBody();
            return res;
        } catch (Exception e) {
            throw new NotFoundException("Cloud error");
        }
    }
}
