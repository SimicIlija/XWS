package com.xml.booking.backendmain.lodging;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.place.Place;
import com.xml.booking.backendmain.reservations.Reservation;

import javax.persistence.*;
import java.util.List;

@Entity
public class Lodging {

    @Id
    @GeneratedValue
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.EAGER)
    private Catalog type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Catalog catagoty;

    @ManyToOne(fetch = FetchType.EAGER)
    private Place place;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lodging_id")
    private List<Reservation> reservations;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "LODGING_CATALOG",
            joinColumns = @JoinColumn(name = "LODGING_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CATALOG_ID", referencedColumnName = "ID"))
    private List<Catalog> additionalServices;


    public Lodging() {
    }


    public Lodging(Catalog type, Catalog catagoty, List<Catalog> additionalServices, String name, Place place) {
        super();
        this.type = type;
        this.catagoty = catagoty;
        this.additionalServices = additionalServices;
        this.name = name;
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Catalog getType() {
        return type;
    }

    public void setType(Catalog type) {
        this.type = type;
    }

    public Catalog getCatagoty() {
        return catagoty;
    }

    public void setCatagoty(Catalog catagoty) {
        this.catagoty = catagoty;
    }

    public List<Catalog> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<Catalog> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
