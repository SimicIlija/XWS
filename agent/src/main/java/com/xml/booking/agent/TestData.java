package com.xml.booking.agent;

import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.agent.accommodation.Accommodation;
import com.xml.booking.agent.accommodation.AccomodationService;
import com.xml.booking.agent.backendMain.BackendMainClient;
import com.xml.booking.agent.messages.Message;
import com.xml.booking.agent.messages.MessageRepository;
import com.xml.booking.agent.messages.MessageService;
import com.xml.booking.agent.optionCatalog.Catalog;
import com.xml.booking.agent.optionCatalog.CatalogRepository;
import com.xml.booking.agent.optionCatalog.CatalogService;
import com.xml.booking.agent.optionCatalog.OptionType;
import com.xml.booking.agent.reservation.ReservationService;
import com.xml.booking.agent.user.User;
import com.xml.booking.agent.user.UserRepository;
import com.xml.booking.agent.user.UserService;
import com.xml.booking.agent.user.UserType;

import backendmain.wsdl.GetDBResponse;
import backendmain.wsdl.TestResponse;

@Component
public class TestData {

	@Autowired
	private UserService userService;
	@Autowired 
	private CatalogService catalogService;
	@Autowired
	private AccomodationService accommodationService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private BackendMainClient backendMainClient;
	
	@PostConstruct
	private void init() {
//		TestResponse response = backendMainClient.testWS("pera");
//		System.out.println(response.getName());
		
//		User u1 = new User("pera", "peric", "adresa", 31231321, "bora@bora", "borabora", UserType.AGENT);
//		u1 = userRepository.save(u1);
//		
//		User u2 = new User("a", "a", "a", 31231321, "aaaa@aaaa", "aaaaaa", UserType.AGENT);
//		u2 = userRepository.save(u2);
//	
//		Catalog c1 = new Catalog(OptionType.TYPE, "tip");
//		catalogRepository.save(c1);
//		
//		Catalog c2 = new Catalog(OptionType.CATEGORY, "dodatna usluga");
//		catalogRepository.save(c2);
//		
//		Catalog c3 = new Catalog(OptionType.ADDITIONAL_SERVICES, "dodatna usluga");
//		catalogRepository.save(c3);
//		
//		ArrayList<Double> list = new ArrayList<>();
//		for(int i=0;i<12;i++) {
//			list.add(0.0);
//		}
//		
//		ArrayList<Catalog> add = new ArrayList<>();
//		add.add(catalogRepository.getOne(3L));
//		
//		Accommodation a1 = new Accommodation(u1, "a", "a", c1, c2, "a", null, 1, add, list);
//		//a1.setId(1L);
//		accommodationRepository.add(a1);
//		
//		Accommodation a2 = new Accommodation(u1, "b", "b", c1, c2, "b", null, 1, add, list);
//		//a2.setId(2L);
//		accommodationRepository.add(a2);
//		
//		Message msgm1 = new Message(u2, u1, 1L, "master", true, null);
//		msgm1 = messageRepository.save(msgm1);
//		messageService.addMessage(msgm1.getId(), new Message(u1, u2, 2L, "slave", false, null));
		
	}
	
}
