package com.xml.booking.agent.accommodation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xml.booking.agent.backendMain.BackendMainClient;
import com.xml.booking.agent.optionCatalog.Catalog;
import com.xml.booking.agent.optionCatalog.CatalogService;
import com.xml.booking.agent.user.UserService;

import backendmain.wsdl.AccommodationXML;
import backendmain.wsdl.AddAccommodationResponse;

@Transactional(readOnly = true)
@Service
public class AccomodationServiceImpl implements AccomodationService {

	@Autowired
	private AccommodationRepository accommodationRepository;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private UserService userService;
	@Autowired
	private BackendMainClient backendMainClient;
	
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
		AddAccommodationResponse response = backendMainClient.addAccommodation(accommodation);
		if(response.getAccommodation() == null)
			return null;
		
		Accommodation tmp = new Accommodation(response.getAccommodation());
		tmp.setAgent(userService.findOne(tmp.getAgent().getId()));
		tmp.setCategory(catalogService.findOne(tmp.getCategory().getId()));
		tmp.setType(catalogService.findOne(tmp.getType().getId()));
		ArrayList<Catalog> addSer = new ArrayList<>();
		for(Catalog service : tmp.getAdditionalServices()) {
			addSer.add(catalogService.findOne(service.getId()));
		}
		tmp.setAdditionalServices(addSer);
		return accommodationRepository.save(tmp);
	}

	@Override
	@Transactional(readOnly = false)
	public void cloneDB(List<AccommodationXML> accommodationsXML) {
		accommodationRepository.deleteAllInBatch();
		for(AccommodationXML accommodationXML : accommodationsXML) {
			Accommodation tmp = new Accommodation(accommodationXML);
			tmp.setAgent(userService.findOne(tmp.getAgent().getId()));
			tmp.setCategory(catalogService.findOne(tmp.getCategory().getId()));
			tmp.setType(catalogService.findOne(tmp.getType().getId()));
			ArrayList<Catalog> addSer = new ArrayList<>();
			for(Catalog service : tmp.getAdditionalServices()) {
				addSer.add(catalogService.findOne(service.getId()));
			}
			tmp.setAdditionalServices(addSer);
			accommodationRepository.save(tmp);
		}
	}
	
}
