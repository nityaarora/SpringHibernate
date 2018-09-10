package com.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.dao.CountryDao;
import com.web.model.Country;
import com.web.service.CountryService;

//Foreign key can have null values
//TODO - save vs persist

@Controller
@RequestMapping(value="countries")
public class CountryController {
	
	@Inject
	CountryService countryService;

	@RequestMapping(value="/get",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country get(@RequestBody Country country){
		
		countryService.insert();
		return new Country();
	}
	
	@RequestMapping(value="/insertOTM",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertOTM(@RequestBody Country country){
		countryService.insertOneTM();
		return new Country();
	}
	
	@RequestMapping(value="/insertcityWithCountry",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertcityWithCountry(@RequestBody Country country){
		countryService.insertCityWithCountry();
		return new Country();
	}
	
	//Convert to json directly - fails if nth else is changed except for parameters
	//if get, post is changed, then works
	//To pass int as parameter, we pass it directly, no need of json
	@RequestMapping(value="/insertcityWithCountryId",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertcityWithCountry(@RequestBody int id){
		countryService.insertCityWithCountry(id);
		return new Country();
	}
	
	//save vs persist
	@RequestMapping(value="/insertcityWithCountryIdWithSave",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertcityWithCountryWithSave(@RequestBody int id){
		countryService.insertCityWithCountryWithSaveNotPersist(id);
		return new Country();
	}
	
	//save
	@RequestMapping(value="/insertcityWithCountryWithSave",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertcityWithCountryWithSave(@RequestBody Country country){
		countryService.insertCityWithCountryWithSave();
		return new Country();
	}
	
	
	@RequestMapping(value="/insertWithTransactional",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertWithTransactional(@RequestBody Country country){
		countryService.insertWithTransactional();
		return new Country();
	}
	
	@RequestMapping(value="/insertWithTransactionalRandom",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertWithTransactionalRandom(@RequestBody Country country){
		Country country1 = new Country();
		countryService.insertWithTransactionalRandom();
		return new Country();
	}
	
	
	@RequestMapping(value="/insertWithTransactionalGetMod",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country insertWithTransactionalGetMod(@RequestBody Country country){
		Country country1 = new Country();
		countryService.insertWithTransactionalGetMod();
		return new Country();
	}
	
	@RequestMapping(value="/modinsideAnotherTransaction",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country modinsideAnotherTransaction(@RequestBody Country country){
		Country country1 = new Country();
		countryService.modinsideAnotherTransaction();
		return new Country();
	}
	
	@RequestMapping(value="/modinsideIso",method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody Country modinsideIso(@RequestBody Country country){
		Country country1 = new Country();
		countryService.modinsideIso();
		return new Country();
	}
	

}
