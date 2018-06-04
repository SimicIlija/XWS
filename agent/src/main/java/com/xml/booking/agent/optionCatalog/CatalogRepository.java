package com.xml.booking.agent.optionCatalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long>{

	List<Catalog> findByType(OptionType optionType);
	
}
