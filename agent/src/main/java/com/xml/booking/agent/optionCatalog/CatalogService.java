package com.xml.booking.agent.optionCatalog;

import java.util.List;

import backendmain.wsdl.CatalogXML;

public interface CatalogService {

	Catalog findOne(Long id);
	
	List<Catalog> findAll();
	
	List<Catalog> findByType(OptionType optionType);
	
	Catalog add(Catalog catalog);
	
	Catalog delete(Long id);
	
	void cloneDB(List<CatalogXML> catalogsXML);
	
}
