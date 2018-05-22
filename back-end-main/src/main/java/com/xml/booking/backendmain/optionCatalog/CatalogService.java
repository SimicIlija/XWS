package com.xml.booking.backendmain.optionCatalog;

import java.util.List;

public interface CatalogService {

	Catalog findOne(Long id);
	
	List<Catalog> findAll();
	
	List<Catalog> findByType(OptionType optionType);
	
	Catalog add(Catalog catalog);
	
	Catalog delete(Long id);
	
	
}
