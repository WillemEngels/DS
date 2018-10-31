package rental;

import java.util.List;

public interface IManagerSession {
	
	
	
	public void registerCarRentalCompany();
	
	
	public void unregisterCarRentalCompany();
	
	
	public List<CarRentalCompany> getAllCarRentalCompanies();
	

	public List<Reservation> getAllReservationsOfCarType();
	
	
	public String getBestCustomer();
	
	
	public CarType getMostPopularCarType();

}
