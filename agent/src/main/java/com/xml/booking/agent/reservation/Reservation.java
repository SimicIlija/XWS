package com.xml.booking.agent.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xml.booking.agent.accommodation.Accommodation;
import com.xml.booking.agent.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne
    private Accommodation accommodation;

    @ManyToOne
    private User user;
    
    @NotNull
    private Long startDate;
    
    @NotNull
    private Long endDate;
    
    @NotNull
    private Boolean confirmed = false;

    public Reservation() {}
    
    public Reservation(Accommodation accommodation, User user, Long startDate, Long endDate) {
		super();
		this.accommodation = accommodation;
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setLodging(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }
}
