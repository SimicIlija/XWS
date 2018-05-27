package com.xml.booking.backendmain.reservations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xml.booking.backendmain.lodging.Lodging;
import com.xml.booking.backendmain.users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lodging_id")
    private Lodging lodging;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Date startDate;

    private Date endDate;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lodging getLodging() {
        return lodging;
    }

    public void setLodging(Lodging lodging) {
        this.lodging = lodging;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Proverava poklapanja datuma,
     * vraca tacno ako se neki od datuma Dto nalazi izmedju start i end date
     * hoce da rezervise na vec postojecu rezervaciju
     */
    public boolean checkDates(ReservationDto dto) {
        boolean start = startDate.before(dto.getStartDate()) && endDate.after(dto.getStartDate());
        boolean end = startDate.before(dto.getEndDate()) && endDate.after(dto.getEndDate());
        return start || end;
    }

    public boolean checkIfNotAvailable(Date newStartDate, Date newEndDate) {
        boolean start = startDate.before(newStartDate) && endDate.after(newStartDate);
        boolean end = startDate.before(newEndDate) && endDate.after(newEndDate);
        return start || end;
    }
}
