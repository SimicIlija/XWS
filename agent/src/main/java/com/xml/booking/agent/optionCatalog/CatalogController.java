package com.xml.booking.agent.optionCatalog;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.booking.agent.user.User;

@RestController
@RequestMapping("/rest/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;
	@Autowired
	private HttpSession session;
	
	@GetMapping("/{type:TYPE|CATEGORY|ADDITIONAL_SERVICES}")
	public ResponseEntity<List<Catalog>> getCatalogs(@PathVariable OptionType type) {
		User user = (User)session.getAttribute("user");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		List<Catalog> catalogs = catalogService.findByType(type);
		if(catalogs == null || catalogs.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(catalogs, HttpStatus.OK);
	}
	
}
