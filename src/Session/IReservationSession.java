package Session;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationException;

public interface IReservationSession {
	
	
	public void createQuote(String name, Date start, Date end, String carType, String region) throws RemoteException, ReservationException;
	
	
	public List<Quote> getCurrentQuotes();
	
	
	public List<Reservation> confirmQuotes(String name) throws Exception;
	
	
	public List<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	
	public CarType getCheapestCarType(Date start, Date end, String region);
	

}
