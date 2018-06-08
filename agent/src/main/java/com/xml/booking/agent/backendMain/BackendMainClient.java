package com.xml.booking.agent.backendMain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.xml.booking.agent.accommodation.Accommodation;
import com.xml.booking.agent.messages.Message;
import com.xml.booking.agent.reservation.Reservation;
import com.xml.booking.agent.user.User;

import backendmain.wsdl.AddAccommodationRequest;
import backendmain.wsdl.AddAccommodationResponse;
import backendmain.wsdl.AddMessageRequest;
import backendmain.wsdl.AddMessageResponse;
import backendmain.wsdl.AddReservationsRequest;
import backendmain.wsdl.AddReservationsResponse;
import backendmain.wsdl.ConfirmReservationRequest;
import backendmain.wsdl.ConfirmReservationResponse;
import backendmain.wsdl.DBRequestType;
import backendmain.wsdl.DeleteReservationRequest;
import backendmain.wsdl.DeleteReservationResponse;
import backendmain.wsdl.GetDBRequest;
import backendmain.wsdl.GetDBResponse;
import backendmain.wsdl.LogInRequest;
import backendmain.wsdl.LogInResponse;
import backendmain.wsdl.TestRequest;
import backendmain.wsdl.TestResponse;
import backendmain.wsdl.UserXML;


public class BackendMainClient extends WebServiceGatewaySupport {
	
	private static final String URI = "http://localhost:8080/ws";
	
	private static final String TOKEN = "TOKEN1234";

	@Autowired
	private ConverterXML converterXML;
	
	public TestResponse testWS(String text) {
		TestRequest request = new TestRequest();
		request.setName(text);
		TestResponse response = (TestResponse) getWebServiceTemplate().marshalSendAndReceive(URI , request, new SoapActionCallback(URI + "/testRequest"));
		return response;
	}
	
	public GetDBResponse getDB(List<DBRequestType> type) {
		GetDBRequest request = new GetDBRequest();
		request.setToken(TOKEN);
		request.getType().addAll(type);
		GetDBResponse response = (GetDBResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/getDBRequest"));
		return response;
	}
	
	public LogInResponse logIn(User user) {
		LogInRequest request = new LogInRequest();
		request.setToken(TOKEN);
		UserXML userXML = new UserXML();
		userXML.setEmail(user.getEmail());
		userXML.setPassword(user.getPassword());
		request.setUser(userXML);
		LogInResponse response = (LogInResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/logInRequest"));
		return response;
	}
	
	public AddAccommodationResponse addAccommodation(Accommodation accommodation) {
		AddAccommodationRequest request = new AddAccommodationRequest();
		request.setToken(TOKEN);
		request.setAccommodation(converterXML.lodgingToAccommodationXML(accommodation));
		AddAccommodationResponse response = (AddAccommodationResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/addAccommodationRequest"));
		return response;
	}
	
	public AddMessageResponse addMessage(Long masterId, Message message) {
		AddMessageRequest request = new AddMessageRequest();
		request.setToken(TOKEN);
		request.setMasterId(masterId);
		request.setMessage(converterXML.messageToMessageXML(message));
		AddMessageResponse response = (AddMessageResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/addMessageRequest"));
		return response;
	}
	
	public AddReservationsResponse addReservations(List<Reservation> reservations) {
		AddReservationsRequest request = new AddReservationsRequest();
		request.setToken(TOKEN);
		for(Reservation reservation : reservations) {
			request.getReservations().add(converterXML.reservationToReservationXML(reservation));
		}
		AddReservationsResponse response = (AddReservationsResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/addReservationsRequest"));
		return response;
	}
	
	public ConfirmReservationResponse confirmReservation(Long id) {
		ConfirmReservationRequest request = new ConfirmReservationRequest();
		request.setToken(TOKEN);
		request.setReservation(id);
		ConfirmReservationResponse response = (ConfirmReservationResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/confirmReservation"));
		return response;
	}
	
	public DeleteReservationResponse deleteReservation(Long id) {
		DeleteReservationRequest request = new DeleteReservationRequest();
		request.setToken(TOKEN);
		request.setReservation(id);
		DeleteReservationResponse response = (DeleteReservationResponse) getWebServiceTemplate().marshalSendAndReceive(URI, request, new SoapActionCallback(URI + "/deleteReservationRequest"));
		return response;
	}
}
