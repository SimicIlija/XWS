package com.xml.booking.backendmain.rating;

public class RatingDto {
    private long idReservation;
    private long idLodging;
    private long idUser;
    private int rating;
    private String comment;
    private boolean confirmed;

    public RatingDto() {
    }

    public long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(long idReservation) {
        this.idReservation = idReservation;
    }

    public long getIdLodging() {
        return idLodging;
    }

    public void setIdLodging(long idLodging) {
        this.idLodging = idLodging;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
