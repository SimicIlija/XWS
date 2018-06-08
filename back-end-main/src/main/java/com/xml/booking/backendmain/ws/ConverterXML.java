package com.xml.booking.backendmain.ws;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingService;
import com.xml.booking.backendmain.messages.Message;
import com.xml.booking.backendmain.messages.MessageService;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.optionCatalog.CatalogService;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.ws_classes.AccommodationXML;
import com.xml.booking.backendmain.ws_classes.CatalogXML;
import com.xml.booking.backendmain.ws_classes.MessageXML;
import com.xml.booking.backendmain.ws_classes.OptionTypeXML;
import com.xml.booking.backendmain.ws_classes.ReservationXML;
import com.xml.booking.backendmain.ws_classes.UserTypeXML;
import com.xml.booking.backendmain.ws_classes.UserXML;

@Component
public class ConverterXML {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private LodgingService lodgingService;
	@Autowired
	private MessageService messageService;
	
	public UserXML userToUserXML(User user) {
		if(user != null) {
			UserXML ret = new UserXML();
			ret.setId(user.getId());
			ret.setFirstName(user.getName());
			ret.setLastName(user.getLastName());
			ret.setAddress(user.getCity());
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
	
	public AccommodationXML lodgingToAccommodationXML(Lodging lodging) {
		if(lodging != null) {
			AccommodationXML ret = new AccommodationXML();
			ret.setId(lodging.getId());
			ret.setAgent(userToUserXML(lodging.getAgent()));
			ret.setName(lodging.getName());
			ret.setAddress(lodging.getLocation());
			ret.setType(catalogToCatalogXML(lodging.getType()));
			ret.setCategory(catalogToCatalogXML(lodging.getCatagoty()));
			ret.setDescription(lodging.getTextDescription());
			for(String image : lodging.getImageUrls())
				ret.getImages().add(image);
			ret.setBadNumber(lodging.getNumberOfGuests());
			for(Catalog service : lodging.getAdditionalServices())
				ret.getAdditionalServices().add(catalogToCatalogXML(service));
			for(Double month : lodging.getPrices())
				ret.getPriceByMonth().add(month);
			return ret;
		}
		return null;
	}
	
	public ReservationXML reservationToReservationXML(Reservation reservation) {
		if(reservation != null) {
			ReservationXML ret = new ReservationXML();
			ret.setId(reservation.getId());
			ret.setAccommodation(lodgingToAccommodationXML(reservation.getLodging()));
			ret.setUser(userToUserXML(reservation.getUser()));
			ret.setStartDate(reservation.getStartDate().getTime());
			ret.setEndDate(reservation.getEndDate().getTime());
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

	public Lodging accommodationXMLToLodging(AccommodationXML accommodationXML) {
		if(accommodationXML != null) {
			Lodging lodging = new Lodging();
			lodging.setId(accommodationXML.getId());
			if(accommodationXML.getAgent() != null)
				lodging.setAgent(userService.findOne(accommodationXML.getAgent().getId()));
			lodging.setName(accommodationXML.getName());
			lodging.setLocation(accommodationXML.getAddress());
			if(accommodationXML.getType() != null)
				lodging.setType(catalogService.findOne(accommodationXML.getType().getId()));
			if(accommodationXML.getCategory() != null)
				lodging.setCatagoty(catalogService.findOne(accommodationXML.getCategory().getId()));
			lodging.setTextDescription(accommodationXML.getDescription());
			lodging.setImageUrls(accommodationXML.getImages());
			lodging.setNumberOfGuests(accommodationXML.getBadNumber());
			lodging.setPrices(accommodationXML.getPriceByMonth());
			lodging.setAdditionalServices(new ArrayList<>());
			for(CatalogXML catalogXML : accommodationXML.getAdditionalServices()) {
				if(catalogXML != null) {
					lodging.getAdditionalServices().add(catalogService.findOne(catalogXML.getId()));
				}
			}
			return lodging;
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
			return message;
		}
		return null;
	}

	public Reservation reservationXMLToReservation(ReservationXML reservationXML) {
		if(reservationXML != null) {
			Reservation reservation = new Reservation();
			
			reservation.setId(reservationXML.getId());
			if(reservationXML.getUser() != null)
				reservation.setUser(userService.findOne(reservationXML.getUser().getId()));
			reservation.setStartDate(new Date(reservationXML.getStartDate()));
			reservation.setEndDate(new Date(reservationXML.getEndDate()));
			if(reservationXML.getAccommodation() != null)
				reservation.setLodging(lodgingService.findOne(reservationXML.getAccommodation().getId()));
			reservation.setConfirmed(reservationXML.isConfirmed());
			return reservation;
		}
		return null;
	}
	
}
