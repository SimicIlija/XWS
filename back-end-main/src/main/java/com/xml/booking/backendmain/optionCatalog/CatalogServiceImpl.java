package com.xml.booking.backendmain.optionCatalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CatalogServiceImpl implements CatalogService{

	@Autowired
	private CatalogRepository catalogRepository;
	
	
	@Override
	public Catalog findOne(Long id) {
		try {
			return catalogRepository.findById(id).get();
		
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Catalog> findAll() {
		return catalogRepository.findAll();
	}

	@Override
	public List<Catalog> findByType(OptionType optionType) {
		return catalogRepository.findByType(optionType);
	}

	@Override
	public Catalog add(Catalog catalog) {
		return catalogRepository.save(catalog);
	}

	@Override
	public Catalog delete(Long id) {
		Catalog catalog = findOne(id);
		if(catalog == null)
			return null;
		try {
			catalogRepository.delete(catalog);
		}catch(Exception e){
			System.out.println("Could not delete Catalogue element");
			return null;
		}
		return catalog;
	}

	
}
