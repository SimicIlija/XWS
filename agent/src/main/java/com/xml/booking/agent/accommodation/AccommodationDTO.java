package com.xml.booking.agent.accommodation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class AccommodationDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@NotNull
	private Long type;
	
	@NotNull
	private Long category;
	
	@NotBlank
	private String description;
	
	@NotNull
	@Size(min=1)
	private List<String> images;
	
	@NotNull
	@Min(1)
	private int badNumber;
	
	private List<Long> additionalServices;
	
	@NotEmpty
	@Size(min = 12, max = 12)
	private List<Double> priceByMonth;

	public AccommodationDTO() {}

	public AccommodationDTO(String name, String address, Long type, Long category, String description, List<String> images, int badNumber, List<Long> additionalServices, List<Double> priceByMonth) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.description = description;
		this.images = images;
		this.badNumber = badNumber;
		this.additionalServices = additionalServices;
		this.priceByMonth = priceByMonth;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
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

	public List<Long> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<Long> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public List<Double> getPriceByMonth() {
		return priceByMonth;
	}

	public void setPriceByMonth(ArrayList<Double> priceByMonth) {
		this.priceByMonth = priceByMonth;
	}
	
}
