package com.xml.booking.backendmain.search;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingRepository;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.rating.RatingDto;
import com.xml.booking.backendmain.rating.RatingService;
import com.xml.booking.backendmain.rating.ResultLodging;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final LodgingRepository lodgingRepository;

    private final ReservationRepository reservationRepository;

    private final RatingService ratingService;

    @Autowired
    public SearchService(LodgingRepository lodgingRepository, ReservationRepository reservationRepository, RatingService ratingService) {
        this.lodgingRepository = lodgingRepository;
        this.reservationRepository = reservationRepository;
        this.ratingService = ratingService;
    }

    public SearchResultDto search(@Valid SearchDto searchDto) {
        List<Lodging> lodgings = lodgingRepository.findByLocationIgnoreCase(searchDto.getPlace());
        List<Lodging> filterNumberOfGuests;
        filterNumberOfGuests = lodgings.stream()
                .filter(lodging -> lodging.getNumberOfGuests() >= searchDto.getNumberOfPeople())
                .collect(Collectors.toList());
        List<Lodging> datesFiltered = filterDates(filterNumberOfGuests, searchDto);
        List<Lodging> additional = filterAdditional(datesFiltered, searchDto);
        if (searchDto.getRating() != 0) {
            List<Lodging> retVal = filterRating(additional, searchDto.getRating());
            return createRetVal(retVal, searchDto);
        }
        return createRetVal(additional, searchDto);
    }

    private SearchResultDto createRetVal(List<Lodging> input, @Valid SearchDto searchDto) {
        SearchResultDto searchResultDto = searchDto.create();
        List<LodgingDto> dtos = new ArrayList<>();
        for(Lodging lodging:input){
            LodgingDto dto = new LodgingDto();
            dto.setLodging(lodging);
            dto.setPrice(calculatePrice(lodging, searchDto.getStartDate(), searchDto.getEndDate(), searchDto.getNumberOfPeople()));
            dto.setAvg(ratingService.getAverageGrade(lodging.getId()));
            dtos.add(dto);
        }
        searchResultDto.setLodgings(dtos);
        return searchResultDto;
    }

    private double calculatePrice(Lodging lodging, Date startDate, Date endDate, int numberOfPeople) {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        double price = 0;
        while (startCalendar.before(endCalendar)) {
            int month = startCalendar.get(Calendar.MONTH);
            price += numberOfPeople * lodging.getPrices().get(month);
            startCalendar.add(Calendar.DATE, 1);
        }
        return price;
    }

    private List<Lodging> filterRating(List<Lodging> input, int rating) {
        List<ResultLodging> ratingDtos = ratingService.filter(rating);
        List<Lodging> retVal = new ArrayList<>();
        for(Lodging lodging:input){
            boolean toAdd = false;
            for(ResultLodging resultLodging:ratingDtos){
                if(lodging.getId().equals(resultLodging.getIdlodging())){
                    toAdd = true;
                }
            }
            if(toAdd){
                retVal.add(lodging);
            }
        }
        return retVal;
    }

    private List<Lodging> filterAdditional(List<Lodging> datesFiltered, @Valid SearchDto searchDto) {
        List<Lodging> retval = new ArrayList<>();
        for (Lodging lodging : datesFiltered) {
            if (searchDto.getType() != null) {
                if (!lodging.getType().getValue().equals(searchDto.getType())) {
                    break;
                }
            }
            if (searchDto.getCategory() != null) {
                if (!lodging.getCatagoty().getValue().equals(searchDto.getCategory())) {
                    break;
                }
            }
            boolean toAdd = true;
            List<Catalog> additional = lodging.getAdditionalServices();
            for (String temp : searchDto.getAdditionalServices()) {
                boolean present = false;
                for (Catalog catalog : additional) {
                    if (catalog.getValue().equals(temp)) {
                        present = true;
                    }
                }
                if (!present) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                retval.add(lodging);
            }
        }
        return retval;
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
