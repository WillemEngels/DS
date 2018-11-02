package Session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Year;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import rental.CarRentalCompany;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Reservation;

public interface IManagerSession extends Remote,ISession{
	
	
	/**********************************************
	 * REGISTERING
	 ******************************************/
	public void registerCarRentalCompany(String name,ICarRentalCompany crc);
	
	public void unregisterCarRentalCompany(String name);
		
	public HashMap<String,ICarRentalCompany> getAllCarRentalCompanies();
	


	
	/*******************************************
	 * INDIVIDUAL CAR RENTAL COMPANY METHODS
	 *****************************************/
	
	public Collection<CarType> getAllCarTypes(ICarRentalCompany crc);
	
	public Set<CarType>getAvailableCarTypes(ICarRentalCompany crc, Date StartDate, Date EndDate) throws RemoteException;
	
	
	
	/************************************
	 * METHODS OVER ALL COMPANIES
	 **************************************/
	
	//public String getBestCustomer();
	public Set<String> getBestCustomers();
	
	
	public CarType getMostPopularCarType(int year, String crc);
	
	public List<Reservation> getAllReservationsOfCarType(String carRentalName, String type);
	




}
