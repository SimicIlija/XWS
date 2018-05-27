package com.xml.booking.backendmain.search;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SearchDto {

    @NotEmpty
    private String place;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    @Min(1)
    private Integer numberOfPeople;

    public SearchDto() {
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

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean checkDates() {
        return endDate.after(startDate);
    }

    public SearchResultDto create() {
        SearchResultDto searchResultDto = new SearchResultDto();
        searchResultDto.setPlace(place);
        searchResultDto.setEndDate(endDate);
        searchResultDto.setStartDate(startDate);
        searchResultDto.setNumberOfPeople(numberOfPeople);
        return searchResultDto;
    }
}
