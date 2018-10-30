package rental;

import java.util.List;

public interface IReservationSession {
	
	
	public void createQuote();
	
	
	public List<Quote> getCurrentQuotes();
	
	
	public List<Reservation> confirmQuotes();
	
	
	public List<CarType> getAvailableCarTypes();
	
	
	public CarType getCheapestCarType();
	

}
