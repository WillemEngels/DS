package NamingService;

import java.util.HashMap;


import rental.ICarRentalCompany;

//Handler maken voor registry dump???

public class NamingService implements INamingService {

	private HashMap<String,ICarRentalCompany> companies;
	
	
	public NamingService(){}
	
	
	public ICarRentalCompany getCarRentalCompany(String name){
		return companies.get(name);
	}
	
	public HashMap<String, ICarRentalCompany> getAllCompanies(){
		return companies;
	}
	
	public synchronized void register(String name, ICarRentalCompany crc){
		companies.put(name, crc);
	}
	
	/*
	 * unregisters company
	 * if company doesn't exist, does nothing
	 */
	public synchronized void unRegister(String name){
		companies.remove(name);
	}
	
	
	
}


