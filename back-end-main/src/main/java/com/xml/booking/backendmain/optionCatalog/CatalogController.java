package com.xml.booking.backendmain.optionCatalog;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xml.booking.backendmain.users.User;
import com.xml.booking.backendmain.users.UserType;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;
	@Autowired
	private HttpSession session;
	
	@GetMapping
	public ResponseEntity<List<Catalog>> getCatalogs(){
		List<Catalog> catalog = catalogService.findAll();
		if(catalog == null || catalog.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(catalog,HttpStatus.OK);
	}
	
	@GetMapping("/{type:TYPE|CATEGORY|ADDITIONAL_SERVICES}")
	public ResponseEntity<List<Catalog>> getCatalogueByType(@PathVariable OptionType optionType){
		List<Catalog> catalog = catalogService.findByType(optionType);
		if(catalog == null || catalog.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(catalog,HttpStatus.OK);
	}
	
	@GetMapping("/{id:\\d+}")
	public ResponseEntity<Catalog> getPlace(@PathVariable Long id) {
		Catalog catalog = catalogService.findOne(id);
		if(catalog == null )
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(catalog,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Catalog> add(@RequestBody Catalog input) {
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Catalog catalog = catalogService.add(input);
		return new ResponseEntity<>(catalog,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id:\\d+}")
	public ResponseEntity<Catalog> delete (@PathVariable Long id)
	{
		User user = (User) session.getAttribute("user");
		if(user == null || !user.getUserType().equals(UserType.SYSADMIN))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		Catalog catalog = catalogService.findOne(id);
		if(catalog == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		catalogService.delete(catalog.getId());
		return new ResponseEntity<>(catalog,HttpStatus.OK);
	}
	
}
