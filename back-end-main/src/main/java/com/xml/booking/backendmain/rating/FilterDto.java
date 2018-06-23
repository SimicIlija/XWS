package com.xml.booking.backendmain.rating;

public class FilterDto {

    private int avRate;

    public FilterDto() {
    }

    public FilterDto(int avRate) {
        this.avRate = avRate;
    }

    public int getAvRate() {
        return avRate;
    }

    public void setAvRate(int avRate) {
        this.avRate = avRate;
    }
}
