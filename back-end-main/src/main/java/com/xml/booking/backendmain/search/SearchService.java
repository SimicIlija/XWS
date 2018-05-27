package com.xml.booking.backendmain.search;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingRepository;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final LodgingRepository lodgingRepository;

    private final ReservationRepository reservationRepository;

    @Autowired
    public SearchService(LodgingRepository lodgingRepository, ReservationRepository reservationRepository) {
        this.lodgingRepository = lodgingRepository;
        this.reservationRepository = reservationRepository;
    }

    public SearchResultDto search(@Valid SearchDto searchDto) {
        List<Lodging> lodgings = lodgingRepository.findByLocationIgnoreCase(searchDto.getPlace());
        List<Lodging> filterNumberOfGuests;
        filterNumberOfGuests = lodgings.stream()
                .filter(lodging -> lodging.getNumberOfGuests() > searchDto.getNumberOfPeople())
                .collect(Collectors.toList());
        List<Lodging> retVal = filterDates(filterNumberOfGuests, searchDto);
        SearchResultDto searchResultDto = searchDto.create();
        searchResultDto.setLodgings(retVal);
        return searchResultDto;
    }

    private List<Lodging> filterDates(List<Lodging> filterNumberOfGuests, @Valid SearchDto searchDto) {
        List<Lodging> retVal = new ArrayList<>();
        for (Lodging lodging : filterNumberOfGuests) {
            List<Reservation> reservations = reservationRepository.findByLodging_Id(lodging.getId());
            boolean toAdd = true;
            for (Reservation reservation : reservations) {
                if (reservation.checkIfNotAvailable(searchDto.getStartDate(), searchDto.getEndDate())) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                retVal.add(lodging);
            }
        }
        return retVal;
    }
}
