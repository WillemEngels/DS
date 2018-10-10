package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List; 
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;  //mag dit?
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class Client extends AbstractTestBooking {
	
	private ICarRentalCompany crc;
	 
	/********
	 * MAIN *
	 ********/
	
	public static void main(String[] args) throws Exception {
		
		String carRentalCompanyName = "Hertz";
		System.setSecurityManager(null);
		
		
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		Client client = new Client("simpleTrips", carRentalCompanyName);
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR 
	 * @throws RemoteException 
	 * @throws NotBoundException *
	 ***************/
	
	public Client(String scriptFile, String carRentalCompanyName) throws RemoteException, NotBoundException {
		super(scriptFile);
		// TODO Auto-generated method stub
		Registry registry = LocateRegistry.getRegistry();
		crc = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
	}
	
	/**
	 * Check which car types are available in the given period
	 * and print this list of car types.
	 *
	 * @param 	start
	 * 			start time of the period
	 * @param 	end
	 * 			end time of the period
	 * @throws RemoteException 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected void checkForAvailableCarTypes(Date start, Date end) throws RemoteException{
		Set<CarType> list = crc.getAvailableCarTypes(start,end);
		for(CarType c : list){
			System.out.println(c.getName());
		}
	}

	/**
	 * Retrieve a quote for a given car type (tentative reservation).
	 * 
	 * @param	clientName 
	 * 			name of the client 
	 * @param 	start 
	 * 			start time for the quote
	 * @param 	end 
	 * 			end time for the quote
	 * @param 	carType 
	 * 			type of car to be reserved
	 * @param 	region
	 * 			region in which car must be available
	 * @return	the newly created quote
	 * @throws ReservationException 
	 *  
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Quote createQuote(String clientName, Date start, Date end,
			String carType, String region) throws RemoteException, ReservationException {
		ReservationConstraints constraint = new ReservationConstraints(start,end,carType,region);
		Quote quote = crc.createQuote(constraint, clientName);
		System.out.println("Quote created \n Renter: "+quote.getCarRenter() + "\n Start Date: " + quote.getStartDate() +
				"\n End Date: " + quote.getEndDate() + "\n Car Type: " + quote.getCarType() 
				+ "\n Rental Company: " + quote.getRentalCompany() + "\n Price: " + quote.getRentalPrice());
		return quote;
		
	}

	/**
	 * Confirm the given quote to receive a final reservation of a car.
	 * 
	 * @param 	quote 
	 * 			the quote to be confirmed
	 * @return	the final reservation of a car
	 * @throws ReservationException 
	 * @throws RemoteException 
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException  {
		return crc.confirmQuote(quote);
	}
	
	/**
	 * Get all reservations made by the given client.
	 *
	 * @param 	clientName
	 * 			name of the client
	 * @return	the list of reservations of the given client
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected List<Reservation> getReservationsByRenter(String clientName) throws RemoteException {
		List<Reservation> list = crc.getReservationsByRenter(clientName);
		for (Reservation res : list){
			System.out.println("Car Type: " + res.getCarType() + "\nCar Id: "+ res.getCarId() + 
					"\nReservation Period: " + res.getStartDate() + " until " + res.getEndDate() + 
					"\n Price: " + res.getRentalPrice());			
		}
		return list;
	}

	/**
	 * Get the number of reservations for a particular car type.
	 * 
	 * @param 	carType 
	 * 			name of the car type
	 * @return 	number of reservations for the given car type
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected int getNumberOfReservationsForCarType(String carType) throws RemoteException {
		int res = crc.getNumberOfReservations(carType);
		System.out.println("Number of Reservations for car type " + carType + ": " + res);
		return res;
	}
}