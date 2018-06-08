package com.xml.booking.backendmain.search;

import com.xml.booking.backendmain.lodging.Lodging;

import java.util.Date;
import java.util.List;

public class SearchResultDto {
    private List<Lodging> lodgings;

    private String place;

    private Date startDate;

    private Date endDate;

    private int numberOfPeople;

    public SearchResultDto() {
    }

    public List<Lodging> getLodgings() {
        return lodgings;
    }

    public void setLodgings(List<Lodging> lodgings) {
        this.lodgings = lodgings;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
