package com.xml.booking.agent.accommodation;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.agent.optionCatalog.Catalog;

@Entity
public class Accommodation {
	
	@Id
	@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	
	@Version
	private long version;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotNull
	@ManyToOne
	private Catalog type;
	
	@NotNull
	@ManyToOne
	private Catalog category;
	
	@NotBlank
	private String description;
	
	@ElementCollection
	@Column(length = 30000000)
	private List<String> images;
	
	@NotNull
	@Min(1)
	private int badNumber;
	
	@ManyToMany
	private List<Catalog> additionalServices;
	
	@NotEmpty
	@Size(min = 12, max = 12)
	@ElementCollection
	private List<Double> priceByMonth;

	public Accommodation() {}

	public Accommodation(String name, String address, Catalog type, Catalog category, String description, List<String> images, int badNumber, List<Catalog> additionalServices, List<Double> priceByMonth) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.category = category;
		this.description = description;
		this.images = images;
		this.badNumber = badNumber;
		this.additionalServices = additionalServices;
		this.priceByMonth = priceByMonth;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Catalog getType() {
		return type;
	}

	public void setType(Catalog type) {
		this.type = type;
	}

	public Catalog getCategory() {
		return category;
	}

	public void setCategory(Catalog category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public int getBadNumber() {
		return badNumber;
	}

	public void setBadNumber(int badNumber) {
		this.badNumber = badNumber;
	}

	public List<Catalog> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<Catalog> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public List<Double> getPriceByMonth() {
		return priceByMonth;
	}

	public void setPriceByMonth(List<Double> priceByMonth) {
		this.priceByMonth = priceByMonth;
	}
	
}
