package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;

public interface ICarRentalCompany extends Remote{
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client)
			throws ReservationException, RemoteException;
	
	public Reservation confirmQuote(Quote quote) throws ReservationException, RemoteException;
	

}
