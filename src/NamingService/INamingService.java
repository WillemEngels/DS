package NamingService;

import java.rmi.Remote;
import java.util.HashMap;

import rental.ICarRentalCompany;

public interface INamingService extends Remote{

	public ICarRentalCompany getCarRentalCompany(String name);
	
	public HashMap<String, ICarRentalCompany> getAllCompanies();
	
	public void register(String name, ICarRentalCompany crc);
	
	public void unRegister(String name);
	
}
