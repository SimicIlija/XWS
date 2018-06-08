package com.xml.booking.agent.backendMain;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.agent.accommodation.Accommodation;
import com.xml.booking.agent.accommodation.AccomodationService;
import com.xml.booking.agent.messages.Message;
import com.xml.booking.agent.messages.MessageService;
import com.xml.booking.agent.optionCatalog.Catalog;
import com.xml.booking.agent.optionCatalog.CatalogService;
import com.xml.booking.agent.reservation.Reservation;
import com.xml.booking.agent.reservation.ReservationService;
import com.xml.booking.agent.user.User;
import com.xml.booking.agent.user.UserService;

import backendmain.wsdl.AccommodationXML;
import backendmain.wsdl.CatalogXML;
import backendmain.wsdl.GetDBResponse;
import backendmain.wsdl.MessageXML;
import backendmain.wsdl.OptionTypeXML;
import backendmain.wsdl.ReservationXML;
import backendmain.wsdl.UserTypeXML;
import backendmain.wsdl.UserXML;

@Component
public class ConverterXML {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private AccomodationService lodgingService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private BackendMainClient backendMainClient;
	@Autowired
	private TruncateDatabaseService truncateDatabaseService;
	
	public void cloneDB() {
		try {
			GetDBResponse response = backendMainClient.getDB(new ArrayList<>());
			truncateDatabaseService.truncate();
			userService.cloneDB(response.getUsers());
			catalogService.cloneDB(response.getCatalogs());
			lodgingService.cloneDB(response.getAccommodations());
			reservationService.cloneDB(response.getReservations());
			messageService.cloneDB(response.getMessages());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserXML userToUserXML(User user) {
		if(user != null) {
			UserXML ret = new UserXML();
			ret.setId(user.getId());
			ret.setFirstName(user.getFirstName());
			ret.setLastName(user.getLastName());
			ret.setAddress(user.getAddress());
			ret.setEmail(user.getEmail());
			ret.setPassword(user.getPassword());
			ret.setWorkId(user.getWorkId());
			ret.setUserType(UserTypeXML.valueOf(user.getUserType().toString()));
			return ret;
		}
		return null;
	}
	
	public CatalogXML catalogToCatalogXML(Catalog catalog) {
		if(catalog != null) {
			CatalogXML ret = new CatalogXML();
			ret.setId(catalog.getId());
			ret.setType(OptionTypeXML.valueOf(catalog.getType().toString()));
			ret.setValue(catalog.getValue());
			return ret;
		}
		return null;
	}
	
	public AccommodationXML lodgingToAccommodationXML(Accommodation lodging) {
		if(lodging != null) {
			AccommodationXML ret = new AccommodationXML();
			ret.setId(lodging.getId());
			ret.setAgent(userToUserXML(lodging.getAgent()));
			ret.setName(lodging.getName());
			ret.setAddress(lodging.getAddress());
			ret.setType(catalogToCatalogXML(lodging.getType()));
			ret.setCategory(catalogToCatalogXML(lodging.getCategory()));
			ret.setDescription(lodging.getDescription());
			for(String image : lodging.getImages())
				ret.getImages().add(image);
			ret.setBadNumber(lodging.getBadNumber());
			for(Catalog service : lodging.getAdditionalServices())
				ret.getAdditionalServices().add(catalogToCatalogXML(service));
			for(Double month : lodging.getPriceByMonth())
				ret.getPriceByMonth().add(month);
			return ret;
		}
		return null;
	}
	
	public ReservationXML reservationToReservationXML(Reservation reservation) {
		if(reservation != null) {
			ReservationXML ret = new ReservationXML();
			if(reservation.getId() != null)
				ret.setId(reservation.getId());
			ret.setAccommodation(lodgingToAccommodationXML(reservation.getAccommodation()));
			ret.setUser(userToUserXML(reservation.getUser()));
			ret.setStartDate(reservation.getStartDate());
			ret.setEndDate(reservation.getEndDate());
			ret.setConfirmed(reservation.getConfirmed());
			return ret;
		}
		return null;
	}
	
	public MessageXML messageToMessageXML(Message message) {
		if(message != null) {
			MessageXML ret = new MessageXML();
			ret.setId(message.getId());
			ret.setSender(userToUserXML(message.getSender()));
			ret.setReceiver(userToUserXML(message.getReceiver()));
			ret.setContent(message.getContent());
			ret.setMaster(message.getMaster());
			ret.setTimeStamp(message.getTimeStamp());
			for(Message message2 : message.getMessages())
				ret.getMessages().add(messageToMessageXML(message2));
			return ret;
		}
		return null;
	}
	
	public User userXMLToUser(UserXML userXML) {
		if(userXML != null) {
			User user = new User();
			user.setEmail(userXML.getEmail());
			user.setPassword(userXML.getPassword());
			return user;
		}
		return null;
	}

	public Accommodation accommodationXMLToLodging(AccommodationXML accommodationXML) {
		if(accommodationXML != null) {
			Accommodation lodging = new Accommodation();
			lodging.setId(accommodationXML.getId());
			if(accommodationXML.getAgent() != null)
				lodging.setAgent(userService.findOne(accommodationXML.getAgent().getId()));
			lodging.setName(accommodationXML.getName());
			lodging.setAddress(accommodationXML.getAddress());
			if(accommodationXML.getType() != null)
				lodging.setType(catalogService.findOne(accommodationXML.getType().getId()));
			if(accommodationXML.getCategory() != null)
				lodging.setCategory(catalogService.findOne(accommodationXML.getCategory().getId()));
			lodging.setDescription(accommodationXML.getDescription());
			lodging.setImages(accommodationXML.getImages());
			lodging.setBadNumber(accommodationXML.getBadNumber());
			lodging.setPriceByMonth(accommodationXML.getPriceByMonth());
			lodging.setAdditionalServices(new ArrayList<>());
			for(CatalogXML catalogXML : accommodationXML.getAdditionalServices()) {
				if(catalogXML != null) {
					lodging.getAdditionalServices().add(catalogService.findOne(catalogXML.getId()));
				}
			}
		}
		return null;
	}

	public Message messageXMLToMessage(MessageXML messageXML) {
		if(messageXML != null) {
			Message message = new Message();
			message.setId(messageXML.getId());
			if(messageXML.getSender() != null)
				message.setSender(userService.findOne(messageXML.getSender().getId()));
			if(messageXML.getReceiver() != null)
				message.setReceiver(userService.findOne(messageXML.getReceiver().getId()));
			message.setContent(messageXML.getContent());
			message.setTimeStamp(messageXML.getTimeStamp());
			message.setMaster(messageXML.isMaster());
			message.setMessages(new ArrayList<>());
			for(MessageXML messageXML2 : messageXML.getMessages()) {
				Message message1 = messageService.findOne(messageXML2.getId());
				if(message1 != null)
				message.getMessages().add(message1);
			}
		}
		return null;
	}

	public Reservation reservationXMLToReservation(ReservationXML reservationXML) {
		if(reservationXML != null) {
			Reservation reservation = new Reservation();
			reservation.setId(reservationXML.getId());
			if(reservationXML.getUser() != null)
				reservation.setUser(userService.findOne(reservationXML.getUser().getId()));
			reservation.setStartDate(reservationXML.getStartDate());
			reservation.setEndDate(reservationXML.getEndDate());
			if(reservationXML.getAccommodation() != null)
				reservation.setAccommodation(lodgingService.findOne(reservationXML.getAccommodation().getId()));
			reservation.setConfirmed(reservationXML.isConfirmed());
		}
		return null;
	}
	
}
