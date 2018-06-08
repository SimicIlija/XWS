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
import com.xml.booking.backendmain.reservations.ReservationServiceImpl;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.ws_classes.AddAccommodationRequest;
import com.xml.booking.backendmain.ws_classes.AddAccommodationResponse;
import com.xml.booking.backendmain.ws_classes.AddMessageRequest;
import com.xml.booking.backendmain.ws_classes.AddMessageResponse;
import com.xml.booking.backendmain.ws_classes.AddReservationsRequest;
import com.xml.booking.backendmain.ws_classes.AddReservationsResponse;
import com.xml.booking.backendmain.ws_classes.ConfirmReservationRequest;
import com.xml.booking.backendmain.ws_classes.ConfirmReservationResponse;
import com.xml.booking.backendmain.ws_classes.DBRequestType;
import com.xml.booking.backendmain.ws_classes.DeleteReservationRequest;
import com.xml.booking.backendmain.ws_classes.DeleteReservationResponse;
import com.xml.booking.backendmain.ws_classes.GetDBRequest;
import com.xml.booking.backendmain.ws_classes.GetDBResponse;
import com.xml.booking.backendmain.ws_classes.LogInRequest;
import com.xml.booking.backendmain.ws_classes.LogInResponse;
import com.xml.booking.backendmain.ws_classes.ReservationXML;
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
	private ReservationServiceImpl reservationService;
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
		
		List<User> users = new ArrayList<>();
		if(request.getType().contains(DBRequestType.USER) || request.getType().isEmpty()) {
			users = userService.findAllNotAdmin();
			if(users == null)
				users = new ArrayList<>();
		}
		
		List<Catalog> catalogs = new ArrayList<>();
		if(request.getType().contains(DBRequestType.CATALOG) || request.getType().isEmpty()) {
			catalogs = catalogService.findAll();
			if(catalogs == null)
				catalogs = new ArrayList<>();
		}
		
		List<Lodging> lodgings = new ArrayList<>();
		if(request.getType().contains(DBRequestType.ACCOMMODATION) || request.getType().isEmpty()) {
			lodgings = lodgingService.findAll();
			if(lodgings == null)
				lodgings = new ArrayList<>();
		}
		
		List<Reservation> reservations = new ArrayList<>();
		if(request.getType().contains(DBRequestType.RSERVATION) || request.getType().isEmpty()) {
			reservations = reservationService.findAll();
			if(reservations == null)
				reservations = new ArrayList<>();
		}
		
		List<Message> messages = new ArrayList<>();
		if(request.getType().contains(DBRequestType.MESSAGE) || request.getType().isEmpty()) {
			messages = messageService.getMasters();
			if(messages == null)
				messages = new ArrayList<>();
		}
		
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
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "logInRequest")
	@ResponsePayload
	public LogInResponse LogInRequest(@RequestPayload LogInRequest request) {
		LogInResponse response = new LogInResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			User dbUser = userService.logIn(converterXML.userXMLToUser(request.getUser()));
			response.setUser(converterXML.userToUserXML(dbUser));
		} catch (Exception e) {
			e.printStackTrace();
			return new LogInResponse();
		}
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addAccommodationRequest")
	@ResponsePayload
	public AddAccommodationResponse addAccommodation(@RequestPayload AddAccommodationRequest request) {
		AddAccommodationResponse response = new AddAccommodationResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			Lodging dbLodging = lodgingService.add(converterXML.accommodationXMLToLodging(request.getAccommodation()));
			response.setAccommodation(converterXML.lodgingToAccommodationXML(dbLodging));
		} catch (Exception e) {
			e.printStackTrace();
			return new AddAccommodationResponse();
		}
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addMessageRequest")
	@ResponsePayload
	public AddMessageResponse addMessage(@RequestPayload AddMessageRequest request) {
		AddMessageResponse response = new AddMessageResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			Message dbMessage = messageService.addMessage(request.getMasterId(), converterXML.messageXMLToMessage(request.getMessage()));
			response.setMessage(converterXML.messageToMessageXML(dbMessage));
		} catch (Exception e) {
			e.printStackTrace();
			return new AddMessageResponse();
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addReservationsRequest")
	@ResponsePayload
	public AddReservationsResponse addReservation(@RequestPayload AddReservationsRequest request) {
		AddReservationsResponse response = new AddReservationsResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			for(ReservationXML reservationXML : request.getReservations()) {
				response.getReservations().add(converterXML.reservationToReservationXML(reservationService.addNew(converterXML.reservationXMLToReservation(reservationXML))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new AddReservationsResponse();
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "confirmReservationRequest")
	@ResponsePayload
	public ConfirmReservationResponse confirmReservation(@RequestPayload ConfirmReservationRequest request) {
		ConfirmReservationResponse response = new ConfirmReservationResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			response.setReservation(converterXML.reservationToReservationXML(reservationService.confirmReservation(request.getReservation())));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteReservationRequest")
	@ResponsePayload
	public DeleteReservationResponse deleteReservation(@RequestPayload DeleteReservationRequest request) {
		DeleteReservationResponse response = new DeleteReservationResponse();
		if(!request.getToken().equals(TOKEN))
			return response;
		try {
			response.setReservation(converterXML.reservationToReservationXML(reservationService.deleteReservation(request.getReservation())));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return response;
	}
}
