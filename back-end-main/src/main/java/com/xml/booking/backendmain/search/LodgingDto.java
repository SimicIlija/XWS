package com.xml.booking.backendmain.search;

import com.xml.booking.backendmain.lodging.Lodging;

public class LodgingDto {
    private Lodging lodging;
    private double avg;
    private double price;

    public LodgingDto() {
    }

    public Lodging getLodging() {
        return lodging;
    }

    public void setLodging(Lodging lodging) {
        this.lodging = lodging;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
