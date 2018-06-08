package com.xml.booking.backendmain;

import com.xml.booking.backendmain.comment.Comment;
import com.xml.booking.backendmain.comment.CommentService;
import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.lodging.LodgingService;
import com.xml.booking.backendmain.messages.Message;
import com.xml.booking.backendmain.messages.MessageService;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.optionCatalog.CatalogService;
import com.xml.booking.backendmain.optionCatalog.OptionType;
import com.xml.booking.backendmain.reservations.Reservation;
import com.xml.booking.backendmain.reservations.ReservationServiceImpl;
import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserService;
import com.xml.booking.backendmain.users.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class TestData {

    private final UserService userService;
    private final CatalogService catalogService;
    private final LodgingService lodgingService;
    private final CommentService commentService;

    private final ReservationServiceImpl reservationService;

    @Autowired
    public TestData(UserService userService, CatalogService catalogService, LodgingService lodgingService, CommentService commentService, ReservationServiceImpl reservationService) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.lodgingService = lodgingService;
        this.commentService = commentService;
        this.reservationService = reservationService;
    }
    
    @Autowired
    private MessageService messageService;

    @PostConstruct
    private void init() {
//		if(userService.findOne(1l) != null)
//			return;

        User user1 = new User("ch@me", "qweqwe", "Chewbacca", "Chewbacca", "Kashyyyk", null);
        user1.setUserType(UserType.AGENT);
        user1.setEmailConfirmed(true);
        userService.register(user1);

        User user2 = new User("rms@me", "qweqwe", "Richard", "Stallman", "New York", null);
        user2.setUserType(UserType.VISITOR);
        user2.setEmailConfirmed(true);
        userService.register(user2);

        User user7 = new User("sys@sys", "qweqwe", "Sheev", "Palpatine", "Naboo", null);
        user7.setUserType(UserType.SYSADMIN);
        user7.setEmailConfirmed(true);
        userService.register(user7);

        Catalog c11 = new Catalog(OptionType.TYPE, "Hotel");
        Catalog c12 = new Catalog(OptionType.TYPE, "bed&breakfast");
        Catalog c13 = new Catalog(OptionType.TYPE, "Apartman");
        catalogService.add(c11);
        catalogService.add(c12);
        catalogService.add(c13);
        
        c11.setValue("telho");
        catalogService.add(c11);

        Catalog c21 = new Catalog(OptionType.CATEGORY, "1 Star");
        Catalog c22 = new Catalog(OptionType.CATEGORY, "2 Star");
        Catalog c23 = new Catalog(OptionType.CATEGORY, "3 Star");
        Catalog c24 = new Catalog(OptionType.CATEGORY, "4 Star");
        Catalog c25 = new Catalog(OptionType.CATEGORY, "5 Star");
        Catalog c26 = new Catalog(OptionType.CATEGORY, "Unknown");
        catalogService.add(c21);
        catalogService.add(c22);
        catalogService.add(c23);
        catalogService.add(c24);
        catalogService.add(c25);
        catalogService.add(c26);

        List<String> additionlPlaces = Arrays.asList("Parking", "WiFi", "Breakfast", "Half board", "Board", "TV", "Mini kitchen/kitchen", "Private bathroom");
        Catalog n[] = new Catalog[additionlPlaces.size()];
        for (int i = 0; i < additionlPlaces.size(); i++) {
            n[i] = new Catalog(OptionType.ADDITIONAL_SERVICES, additionlPlaces.get(i));
            catalogService.add(n[i]);
        }

        List<Catalog> list1 = Arrays.asList(n[0], n[1], n[2]);
        Lodging l1 = new Lodging(user1, c11, c21, list1, "Lodging 1",
                "Novi Sad", "Opis objekta", 5, Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0 ,0.0 ,0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        lodgingService.add(l1);

        Reservation reservation = new Reservation();
        reservation.setLodging(l1);
        reservation.setUser(user2);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        reservation.setStartDate(cal.getTime());
        cal.add(Calendar.DATE, 5);
        reservation.setEndDate(cal.getTime());
        reservationService.addNew(reservation);
        Comment c1 = new Comment(user1, l1, "First comment", (long) 1527026814, false);
        Comment c2 = new Comment(user2, l1, "Second comment", (long) 1527026814, false);
        commentService.add(c1);
        commentService.add(c2);
        
        Reservation reservation2 = new Reservation();
        reservation2.setLodging(l1);
        reservation2.setUser(user2);
        reservation2.setStartDate(new Date());
        reservation2.setEndDate(new Date());
        reservationService.addNew(reservation2);
        
        
        Message msg1 = new Message(user2, user1, System.currentTimeMillis(), "Pitanje bla bla bla", true, null);
        messageService.addMessage(-1L, msg1);
    }
}
