package com.xml.booking.agent.optionCatalog;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import backendmain.wsdl.CatalogXML;

@Entity
public class Catalog {

	@Id
	//@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@Version
	private Long version;
	
	@Enumerated(EnumType.STRING)
	private OptionType type;
	
	private String value;
	
	public Catalog(){}

	public Catalog( OptionType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public Catalog(CatalogXML catalogXML) {
		this.id = catalogXML.getId();
		this.type = OptionType.valueOf(catalogXML.getType().toString());
		this.value = catalogXML.getValue();
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

	public OptionType getType() {
		return type;
	}

	public void setType(OptionType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
