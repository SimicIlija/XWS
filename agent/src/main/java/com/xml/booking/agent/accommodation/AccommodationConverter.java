package com.xml.booking.agent.accommodation;

import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xml.booking.agent.optionCatalog.Catalog;
import com.xml.booking.agent.optionCatalog.CatalogService;
import com.xml.booking.agent.optionCatalog.OptionType;
import com.xml.booking.agent.user.User;

@Component
public class AccommodationConverter {
	
	@Autowired
	private CatalogService catalogService;
	
	public Accommodation fromDTO(User agent, AccommodationDTO dto) {
		
		Catalog type = catalogService.findOne(dto.getType());
		if(type == null || type.getType().compareTo(OptionType.TYPE) != 0)
			return null;
		
		Catalog category = catalogService.findOne(dto.getCategory());
				if(category == null || category.getType().compareTo(OptionType.CATEGORY) != 0)
					return null;
				
		ArrayList<Catalog> services = new ArrayList<>();
		for (Long id : dto.getAdditionalServices()) {
			Catalog service = catalogService.findOne(id);
			if(service == null || service.getType().compareTo(OptionType.ADDITIONAL_SERVICES) != 0)
				return null;
			services.add(service);
		}
		
		for (String img : dto.getImages()) {
			String split[] = img.split(",");
			if(!split[0].equals("data:image/jpeg;base64") || !Base64.isBase64(split[1]))
				return null;
		}
		
		return new Accommodation(agent, dto.getName(), dto.getAddress(), type, category, dto.getDescription(), dto.getImages(), dto.getBadNumber(), services, dto.getPriceByMonth());
	}

}
