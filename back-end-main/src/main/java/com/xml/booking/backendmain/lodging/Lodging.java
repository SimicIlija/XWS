package com.xml.booking.backendmain.lodging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.backendmain.optionCatalog.Catalog;
import com.xml.booking.backendmain.users.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
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
	private User agent;

    @ManyToOne(fetch = FetchType.EAGER)
    private Catalog type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Catalog catagoty;

    private String name;

    private String location;

    private String textDescription;

    @Min(1)
    private int numberOfGuests;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Column(length = 30000000)
    private List<String> imageUrls = new ArrayList<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Size(min=12, max=12)
    private List<Double> prices = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "LODGING_CATALOG",
            joinColumns = @JoinColumn(name = "LODGING_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CATALOG_ID", referencedColumnName = "ID"))
    private List<Catalog> additionalServices;


    public Lodging() {
    }


    public Lodging(User agent, Catalog type, Catalog catagoty, List<Catalog> additionalServices,
                   String name, String location, String textDescription, int numberOfGuests, List<Double> prices) {
        super();
        this.agent = agent;
        this.type = type;
        this.catagoty = catagoty;
        this.additionalServices = additionalServices;
        this.name = name;
        this.location = location;
        this.textDescription = textDescription;
        this.numberOfGuests = numberOfGuests;
        this.prices = prices;
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

    public User getAgent() {
		return agent;
	}


	public void setAgent(User agent) {
		this.agent = agent;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }
}
