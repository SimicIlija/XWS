package com.xml.booking.backendmain.search;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private int rating;

    private String type;

    private String category;

    private List<String> additionalServices;

    public SearchDto() {
        additionalServices = new ArrayList<>();
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
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
