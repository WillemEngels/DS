package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List; 
import java.util.Set;

import Session.ISessionManager;
import Session.ManagerSession;
import Session.ReservationSession;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class Client extends AbstractTestManagement<ReservationSession, ManagerSession> {
	
	private ISessionManager sessionManager;
	 
	/********
	 * MAIN *
	 ********/
	
	public static void main(String[] args) throws Exception {
		
		String sessionManagerName = "SessionManager";
		System.setSecurityManager(null);
		
		
		Client client = new Client("trips", sessionManagerName);
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR 
	 * @throws RemoteException 
	 * @throws NotBoundException *
	 ***************/
	
	public Client(String scriptFile, String sessionManagerName) throws RemoteException, NotBoundException {
		super(scriptFile);
		Registry registry = LocateRegistry.getRegistry();
		sessionManager = (ISessionManager) registry.lookup(sessionManagerName);
	}
	
	

	@Override
	protected Set<String> getBestClients(ManagerSession ms) throws Exception {
		return ms.getBestCustomers();
	}

	@Override
	protected String getCheapestCarType(ReservationSession session, Date start, Date end, String region)
			throws Exception {
		return session.getCheapestCarType(start, end, region).getName();
	}

	@Override
	protected CarType getMostPopularCarTypeIn(ManagerSession ms, String carRentalCompanyName, int year)
			throws Exception {
		return ms.getMostPopularCarType(year, carRentalCompanyName);
	}

	@Override
	protected ReservationSession getNewReservationSession(String name) throws Exception {
		return (ReservationSession) sessionManager.createReservationSession(name);
		
	}

	@Override
	protected ManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
		return (ManagerSession) sessionManager.createManagerSession(name, carRentalName);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session, Date start, Date end) throws Exception {
		List<CarType> list = session.getAvailableCarTypes(start, end);
		for(CarType c : list) {
			System.out.println(c.getName());
		}
		
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, String name, Date start, Date end, String carType,
			String region) throws Exception {
		session.createQuote(name, start, end, carType, region);
		
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session, String name) throws Exception {
		return session.confirmQuotes(name);
	}

	@Override
	protected int getNumberOfReservationsBy(ManagerSession ms, String clientName) throws Exception {
		return ms.getAllRentersReservations().size();
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms, String carRentalName, String carType)
			throws Exception {
		return ms.getAllReservationsOfCarType(carRentalName, carType).size();
	}
}