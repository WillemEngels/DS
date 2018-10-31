package Session;

import java.rmi.RemoteException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import rental.CarRentalCompany;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Reservation;

public class ManagerSession extends Session implements IManagerSession{

	public ManagerSession(String name) throws RemoteException {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	/****************************************
	 * REGISTERING
	 *****************************************/
	
	@Override
	public void registerCarRentalCompany(String name, ICarRentalCompany crc) {
		getNamingService().register(name, crc);
	}

	@Override
	public void unregisterCarRentalCompany(String name) {
		getNamingService().unRegister(name);
		
	}

	@Override
	public HashMap<String, ICarRentalCompany> getAllCarRentalCompanies() {
		return getNamingService().getAllCompanies();
	}

	
	
	/*******************************************
	 * INDIVIDUAL CAR RENTAL COMPANY METHODS
	 *****************************************/
	@Override
	public Collection<CarType> getAllCarTypes(ICarRentalCompany crc) {
		return crc.getAllCarTypes();
	}

	@Override
	public Set<CarType> getAvailableCarTypes(ICarRentalCompany crc, Date StartDate, Date EndDate) throws RemoteException {
		return crc.getAvailableCarTypes(StartDate,EndDate);
	}

	
	/********************************************
	 * METHODS OVER ALL COMPANIES
	 *******************************************/
	
	
	@Override
	public List<Reservation> getAllReservationsOfCarType(String type) {
		Collection<ICarRentalCompany> companies = getNamingService().getAllCompanies().values();
		List<Reservation> list = new ArrayList<Reservation>();
		for(ICarRentalCompany crc : companies){
			list.addAll(crc.getReservationsOfCarType(type));
		}
		return list;
	}

	@Override
	public String getBestCustomer() {
		HashMap<String,Integer> rentersRes = getAllRentersReservations();
		//find maximum key associated with max value (MOET ELEGANTER KUNNEN)
		HashMap.Entry<String, Integer> maxEntry = null;
		for (HashMap.Entry<String, Integer> entry : rentersRes.entrySet())
		{
		    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
		}
		return maxEntry.getKey();
	}
	
	//Elegantere manier???
	public HashMap<String,Integer> getAllRentersReservations(){
		HashMap<String,Integer> rentersRes = new HashMap<String,Integer>(); //values van hashmap optellen is er betere manier??
		for(ICarRentalCompany crc : getAllCarRentalCompanies().values()){
		
			for(String renter : crc.getRenterReservations().keySet()){
				if (rentersRes.containsKey(renter)) rentersRes.put(renter, crc.getRenterReservations().get(renter));
				else rentersRes.put(renter, rentersRes.get(renter)+crc.getRenterReservations().get(renter));
			}
		}
		return rentersRes;
	}

	@Override
	public String getMostPopularCarType(int year, ICarRentalCompany crc) {
		return crc.getMostPopularCarType(year);
	}

	
	

}
