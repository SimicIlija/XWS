package com.xml.booking.backendmain.ws;

import org.springframework.stereotype.Component;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.messages.Message;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.ws_classes.AccommodationXML;
import com.xml.booking.backendmain.ws_classes.CatalogXML;
import com.xml.booking.backendmain.ws_classes.MessageXML;
import com.xml.booking.backendmain.ws_classes.OptionTypeXML;
import com.xml.booking.backendmain.ws_classes.ReservationXML;
import com.xml.booking.backendmain.ws_classes.UserTypeXML;
import com.xml.booking.backendmain.ws_classes.UserXML;

@Component
public class ConverterXML {
	
	public UserXML userToUserXML(User user) {
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
	
	public CatalogXML catalogToCatalogXML(Catalog catalog) {
		CatalogXML ret = new CatalogXML();
		ret.setId(catalog.getId());
		ret.setType(OptionTypeXML.valueOf(catalog.getType().toString()));
		ret.setValue(catalog.getValue());
		return ret;
	}
	
	public AccommodationXML lodgingToAccommodationXML(Lodging lodging) {
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
	
	public ReservationXML reservationToReservationXML(Reservation reservation) {
		ReservationXML ret = new ReservationXML();
		ret.setId(reservation.getId());
		ret.setAccommodation(lodgingToAccommodationXML(reservation.getLodging()));
		ret.setUser(userToUserXML(reservation.getUser()));
		ret.setStartDate(reservation.getStartDate().getTime());
		ret.setEndDate(reservation.getEndDate().getTime());
		ret.setConfirmed(reservation.getConfirmed());
		return ret;
	}
	
	public MessageXML messageToMessageXML(Message message) {
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
	
}
