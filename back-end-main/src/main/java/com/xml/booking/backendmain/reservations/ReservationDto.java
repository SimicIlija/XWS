package com.xml.booking.backendmain.reservations;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReservationDto {
    @Min(0)
    private long lodgingId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

    public ReservationDto() {
    }

    public long getLodgingId() {
        return lodgingId;
    }

    public void setLodgingId(long lodgingId) {
        this.lodgingId = lodgingId;
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

    public boolean checkInputDates() {
        return endDate.after(startDate);
    }

}
