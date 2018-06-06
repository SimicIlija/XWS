package com.xml.booking.agent.accommodation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AccomodationServiceImpl implements AccomodationService {

	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Override
	public Accommodation findOne(Long id) {
		return accommodationRepository.findOne(id);
	}

	@Override
	public List<Accommodation> findAll() {
		return accommodationRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Accommodation add(Accommodation accommodation) {
		return accommodationRepository.save(accommodation);
	}

}
