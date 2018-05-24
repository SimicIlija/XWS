package com.xml.booking.backendmain.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Place addNew(String name) {
        Place place = new Place();
        place.setName(name);
        place = placeRepository.save(place);
        return place;
    }

    public void update(Place place) {
        placeRepository.save(place);
    }
}
