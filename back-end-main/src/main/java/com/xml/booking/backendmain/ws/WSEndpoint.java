package com.xml.booking.backendmain.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingService;
import com.xml.booking.backendmain.messages.Message;
import com.xml.booking.backendmain.messages.MessageService;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.optionCatalog.CatalogService;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationService;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.ws_classes.GetDBRequest;
import com.xml.booking.backendmain.ws_classes.GetDBResponse;
import com.xml.booking.backendmain.ws_classes.TestRequest;
import com.xml.booking.backendmain.ws_classes.TestResponse;

@Endpoint
public class WSEndpoint {
	private static final String NAMESPACE_URI = "http://booking.xml.com/backendmain/ws-classes";
	
	private static final String TOKEN = "TOKEN1234";
	
	@Autowired
	private UserService userService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private LodgingService lodgingService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ConverterXML converterXML;
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "testRequest")
	@ResponsePayload
	public TestResponse testRequest(@RequestPayload TestRequest request) {
		TestResponse response = new TestResponse();
		response.setName(request.getName().toUpperCase());
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDBRequest")
	@ResponsePayload
	public GetDBResponse getDBRequest(@RequestPayload GetDBRequest request) {
		GetDBResponse response = new GetDBResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		
		List<User> users = userService.findAllNotAdmin();
		if(users == null)
			users = new ArrayList<>();
		
		List<Catalog> catalogs = catalogService.findAll();
		if(catalogs == null)
			catalogs = new ArrayList<>();
		
		List<Lodging> lodgings = lodgingService.findAll();
		if(lodgings == null)
			lodgings = new ArrayList<>();
		
		List<Reservation> reservations = reservationService.findAll();
		if(reservations == null)
			reservations = new ArrayList<>();
		
		List<Message> messages = messageService.getMasters();
		if(messages == null)
			messages = new ArrayList<>();
		
		for (User user : users)
			response.getUsers().add(converterXML.userToUserXML(user));
		
		for(Catalog catalog : catalogs)
			response.getCatalogs().add(converterXML.catalogToCatalogXML(catalog));
		
		for(Lodging lodging : lodgings)
			response.getAccommodations().add(converterXML.lodgingToAccommodationXML(lodging));
		
		for(Reservation reservation : reservations)
			response.getReservations().add(converterXML.reservationToReservationXML(reservation));
		
		for(Message message : messages)
			response.getMessages().add(converterXML.messageToMessageXML(message));
			
		
		return response;
	}
}
