package Session;

import java.util.List;

import rental.CarType;
import rental.Quote;
import rental.Reservation;

public interface IReservationSession {
	
	
	public void createQuote();
	
	
	public List<Quote> getCurrentQuotes();
	
	
	public List<Reservation> confirmQuotes();
	
	
	public List<CarType> getAvailableCarTypes();
	
	
	public CarType getCheapestCarType();
	

}
