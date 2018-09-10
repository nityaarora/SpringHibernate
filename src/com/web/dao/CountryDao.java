package com.web.dao;

import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.domain.City;
import com.web.domain.Country;
import com.web.service.CountryService;

//If u dont close the session, on server restart, all sessions are auto killed
// If multi threads call the transaction method, every time, a new session is created
// if begin transaction is given multiple times, then same session si fetched always
// TO create multiple sessions, use openSession multi times and call begintransaction on each of them

@Repository               // Check abt this
public class CountryDao {
	
	@Inject
	SessionFactory sessionFactory;
	
	@Inject
	CountryService countryService; 
	
	public void insert(){
		
		Country country = new Country(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"India", "Hindi");
		Session session=null;
		Session session2=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.persist(country);
			
			Thread.sleep(20000);
			
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	//This method inserts country and city links into another table as onetomany relation, foreign key in city is not updated
	public void insertOneTM(){
		
		Country country = new Country(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"India", "Hindi");
		City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
		City city2 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Noida");
		country.setCities(Arrays.asList(city1, city2));
		Session session=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.persist(city1);
			session.persist(city2);
			session.persist(country);
			
			
//			Thread.sleep(20000);
			
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	
	//Saw here that manytoone will look only for join id and nth else in the object...not necessary
	// to fetch the object first - try to get the country object and then perform persist in city
	public void insertCityWithCountry(){
		
		Country country = new Country(368,"India", "Hindi");
		City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
		city1.setCountry(country);
		Session session=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.persist(city1);
			
//			Thread.sleep(20000);
			
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	public void insertCityWithCountryWithSave(){
		
		Country country = new Country(368,"India", "Hindi");
		City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
		city1.setCountry(country);
		Session session=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.save(city1);
			
//			Thread.sleep(20000);
			
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	
	//internally hibernate uses a default constructor and further assigns values using setters 
	public Country getCountryFromDb(int id){
		
		
		Session session=null;
		Transaction transaction=null;
		Country country =null;
		try{
			
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Country where id = :id");
			query.setInteger("id", id);
			country = (Country)query.uniqueResult();
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
		return country;
	}
	
	//persist always calling select statement before insert
	public void insertCityWithCountry(int id){
	
		Country country = getCountryFromDb(id);
		City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
		city1.setCountry(country);
		Session session=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.persist(city1);
//			Thread.sleep(20000);
			
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	public void insertCityWithCountryWithSaveNotPersist(int id){
		
		Country country = getCountryFromDb(id);
		City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
		city1.setCountry(country);
		Session session=null;
		Transaction transaction=null;
		try{
			session = sessionFactory.openSession();
//			session2 = sessionFactory.openSession();
			transaction = session.beginTransaction();
//			session2.beginTransaction();
			session.save(city1);
//			Thread.sleep(20000);
			transaction.commit();
		}
		catch(Exception e){
			if(transaction!=null)
				transaction.rollback();
		}finally{
			if(session!=null)
				session.close();
		}
	}
	
	//country cannot be persisted since city is not persisted, and thus, constraint violation for country_city
	public void insertWithTransactional(){
		Country country = new Country(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"India", "Hindi");
		//City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
	//	country.setCities(Arrays.asList(city1));
		Session session = sessionFactory.getCurrentSession();
		session.persist(country);
	}
	
	
	public void insertWithTransactionalRandom(){
		Country country = new Country(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"India", "Hindi");
		//City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
	//	country.setCities(Arrays.asList(city1));
		Session session = sessionFactory.getCurrentSession();
		session.persist(country);
		session.persist(country);		// 2 persist - no difference
		country.setId(189);				// cannot update this as id cannot be changed after persist is called
		country.setName("India2");		// this persist the latest value in country object
	}
	
	public void insertWithTransactionalMultiple(){
		
		Country country = new Country(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"India", "Hindi");
		//City city1 = new City(Integer.parseInt(String.valueOf(Math.random()*1000).substring(0,3)),"Delhi");
	//	country.setCities(Arrays.asList(city1));
		Session session = sessionFactory.getCurrentSession();
		session.persist(country);
//		country.setId(189);				
		session.persist(country);		// 2 persist
		country.setName("India2");
	}
	
	// this case crashes as no default costructor for country
	// using evict without flush or clear modifies no changes made
	// using flush before evict makes changes if commit is done....
	public void insertWithTransactionalGetMod(){
		
		Country countryGet = getCountryFromDb(842);
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Country where id = :id");
		query.setInteger("id", 463);
		Country country = (Country)query.uniqueResult();
//		session.clear();
		country.setName("Argen2");				// update command running without using evict or flush or clear
		session.flush();
		session.evict(country);
	}
	
	// If changes in country are done in new tansaction, the changes are committed irrespective of other transaction commit or rollback
	public void modinsideAnotherTransaction(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Country where id = :id");
		query.setInteger("id", 463);
		Country country = (Country)query.uniqueResult();
		try{
			countryService.modinsideAnotherTransaction2(country);
		}catch(Exception e){}
			
//		country.setName("Mod3");
		try{
			int a=1/0;
		}catch(Exception e){}
		
		System.out.println(country);
	}
	
	// if we throw run time exception, then rollback happens, but object state for any other transaction will not be affected
	// Rollback in case of only run time exceptions, which are not in try catch i.e catched in throwing for exception aspect
	// one transaction has no impact on other. both working independently,
	public void modinsideAnotherTransaction2(Country country) throws FileNotFoundException{
		
		country.setName("Mod");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Country where id = :id");
		query.setInteger("id", 368);
		
		Country country1 = (Country)query.uniqueResult();
		country1.setName("INDIA_N4");
//		try{
		throw new FileNotFoundException();
//		}catch(Exception e){}
		
	}
	
	// If we close the session manually, then also, hibernate will commit the data
	public void modinsideIso(){
		Session session = sessionFactory.getCurrentSession();
		Country countery = new Country();
		countery.setId(463);
		countery = session.get(Country.class, countery.getId());
		countery.setName("12345");
		try{
			session.flush();	
		}catch(OptimisticLockingFailureException e){
			System.out.println("Optimistic");
		}catch(Exception e){
			System.out.println("Normal "+e.getMessage());
		}
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		}finally{
//			session.close();
		}
		
	}
}
