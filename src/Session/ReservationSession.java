package Session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import rental.CarType;
import rental.Quote;
import rental.Reservation;

public class ReservationSession extends Session implements IReservationSession{

	
	private List quotesList;
	
	public ReservationSession(String name) throws RemoteException {
		super(name);
		quotesList = new ArrayList();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createQuote() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Quote> getCurrentQuotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> confirmQuotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CarType> getAvailableCarTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarType getCheapestCarType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
