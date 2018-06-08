package com.xml.booking.backendmain.reservations;

import com.xml.booking.backendmain.exceptions.AuthException;
import com.xml.booking.backendmain.exceptions.BadRequestException;
import com.xml.booking.backendmain.exceptions.NotFoundException;
import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingRepository;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final LodgingRepository lodgingRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, LodgingRepository lodgingRepository) {
        this.reservationRepository = reservationRepository;
        this.lodgingRepository = lodgingRepository;
    }

    public Reservation addNew(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation addNew(@Valid ReservationDto reservationDto, User user) {
        if (!reservationDto.checkInputDates()) {
            throw new BadRequestException("End date should be after start date");
        }
        Lodging lodging = lodgingRepository
                .findById(reservationDto.getLodgingId())
                .orElseThrow(() -> new NotFoundException("Lodging not found"));
        List<Reservation> reservations = reservationRepository.findByLodging_Id(reservationDto.getLodgingId());
        for (Reservation reservation : reservations) {
            if (reservation.checkDates(reservationDto)) {
                throw new BadRequestException("Lodging already booked");
            }
        }
        if(lodging.getNumberOfGuests() < reservationDto.getNumberOfPeople()){
            throw new BadRequestException("Not enough place of all guests :/");
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLodging(lodging);
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setNumberOfPeople(reservationDto.getNumberOfPeople());
        return reservationRepository.save(reservation);
    }

    public void cancelResrvation(long id, User user) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
        System.out.println(user.getEmail());
        if (!reservation.getUser().equals(user)) {
            throw new AuthException("User cannot cancel other users reservation");
        }
        reservationRepository.delete(reservation);
    }
    
    public Boolean canSendMessage(Long userId, Long agentId) {
    	List<Reservation> reservations = reservationRepository.findByLodging_Agent_IdAndUser_Id(agentId, userId);
    	if(reservations == null || reservations.isEmpty())
    		return false;
    	return true;
    }

    public List<Reservation> findByUser(User user) {
        return reservationRepository.findByUser_Id(user.getId());
    }

    public List<Reservation> findByUserHistory(User user) {
        List<Reservation> all = reservationRepository.findByUser_Id(user.getId());
        Date date = new Date();
        return all.stream()
                .filter(reservation -> reservation.getEndDate().before(date))
                .collect(Collectors.toList());
    }

    public List<Reservation> findByUserFuture(User user) {
        List<Reservation> all = reservationRepository.findByUser_Id(user.getId());
        Date date = new Date();
        return all.stream()
                .filter(reservation -> reservation.getStartDate().after(date))
                .collect(Collectors.toList());
    }
}