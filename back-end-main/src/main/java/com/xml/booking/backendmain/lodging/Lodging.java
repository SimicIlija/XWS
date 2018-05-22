package com.xml.booking.backendmain.lodging;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.xml.booking.backendmain.optionCatalog.Catalog;

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
	
	private String name;
	
	 @ManyToMany
	 @JoinTable(
	     name="LODGING_CATALOG",
	     joinColumns=@JoinColumn(name="LODGING_ID", referencedColumnName="ID"),
	     inverseJoinColumns=@JoinColumn(name="CATALOG_ID", referencedColumnName="ID"))
	 private List<Catalog> additionalServices;

	 
	 public Lodging () {}
	 
	 
	public Lodging(Catalog type, Catalog catagoty, List<Catalog> additionalServices,String name) {
		super();
		this.type = type;
		this.catagoty = catagoty;
		this.additionalServices = additionalServices;
		this.name = name;
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
	 
	 
	
}
