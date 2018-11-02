package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Year;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ICarRentalCompany extends Remote{
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws ReservationException, RemoteException;
	
	public Reservation confirmQuote(Quote quote) throws ReservationException, RemoteException;
	
	public List<Reservation> getReservationsByRenter(String renter) throws RemoteException;
	
	public int getNumberOfReservations(String type) throws RemoteException;

	public Collection<CarType> getAllCarTypes();

	public List<Reservation> getReservationsOfCarType(String type);

	public String getMostPopularCarType(int year);
	
	public HashMap<String,Integer> getRenterReservations();
	
	public CarType getCarType(String carTypeName);

	public CarType getCheapestCarType(Date start, Date end, String region);

	public Double getCheapestCarTypePrice(CarType carType, Date start, Date end);
	
	public boolean isLocatedInRegion(String region);

	public void cancelReservation(Reservation res);
	}