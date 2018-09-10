package com.web.service;

import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.dao.CountryDao;
import com.web.domain.Country;

@Service
public class CountryService {
	int j=0;
	
	@Inject
	CountryDao  countryDao;
	
	public void insert(){
		countryDao.insert();
	}

	public void insertOneTM(){
		countryDao.insertOneTM();
	}
	
	public void insertCityWithCountry(){
		countryDao.insertCityWithCountry();
	}
	public void insertCityWithCountryWithSave(){
		countryDao.insertCityWithCountryWithSave();
	}
	
	public void insertCityWithCountry(int id){
		countryDao.insertCityWithCountry(id);
	}
	
	public void insertCityWithCountryWithSaveNotPersist(int id){
		countryDao.insertCityWithCountryWithSaveNotPersist(id);
	}
	
	//Never create with private methods as then spring wont be able to access it via proxy subclass(not interface)
	@Transactional
	public void insertWithTransactional(){
		countryDao.insertWithTransactional();
	}
	
	@Transactional
	public void insertWithTransactionalRandom(){
//		this.insertWithTransactionalWithNew();				// this thing here calls to the object and not to the proxy object
//		insertWithTransactionalWithNew();					// this also refers the same object
		countryDao.insertWithTransactionalRandom();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void insertWithTransactionalWithNew(){
		countryDao.insertWithTransactional();
	}
	
	@Transactional
	public void insertWithTransactionalGetMod(){
		countryDao.insertWithTransactionalGetMod();
	}
	
	@Transactional
	public void modinsideAnotherTransaction(){
		countryDao.modinsideAnotherTransaction();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void modinsideAnotherTransaction2(Country country) throws FileNotFoundException{
		countryDao.modinsideAnotherTransaction2(country);
	}
	 
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void modinsideIso(){
		String str="abcv";
		countryDao.modinsideIso();
	}
	
}
