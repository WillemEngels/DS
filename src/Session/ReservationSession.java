package Session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession extends Session implements IReservationSession{

	
	private HashMap<Quote, ICarRentalCompany> quotesMap;
	
	public ReservationSession(String name) throws RemoteException {
		super(name);
		quotesMap = new HashMap<Quote, ICarRentalCompany>();
		
	}

	@Override 
	public void createQuote(String clientName, Date start, Date end, String carType, String region) throws RemoteException, ReservationException {
		
		List<ICarRentalCompany> allCompanies = (List<ICarRentalCompany>) getNamingService().getAllCompanies().values();
		List<ICarRentalCompany> companiesToBeRemoved = new ArrayList<ICarRentalCompany>();
		
		for(ICarRentalCompany crc : allCompanies) {
			if(!crc.isLocatedInRegion(region)) {
				companiesToBeRemoved.add(crc);
			}
		}
		
		allCompanies.removeAll(companiesToBeRemoved);
		companiesToBeRemoved.clear();
		
		for(ICarRentalCompany crc : allCompanies) {
			if (!crc.getAvailableCarTypes(start, end).contains(carType)) {
				companiesToBeRemoved.add(crc);
			}
		}
		
		allCompanies.removeAll(companiesToBeRemoved);
		
		if(allCompanies.size() > 0) {
			ICarRentalCompany chosenCompany = allCompanies.get(0);
			
			ReservationConstraints resConstraints = new ReservationConstraints(start, end, carType, region);
			Quote quote = chosenCompany.createQuote(resConstraints, clientName);
			quotesMap.put(quote, chosenCompany);
		}
	}

	@Override
	public List<Quote> getCurrentQuotes() {
		return (List<Quote>) quotesMap.keySet();
	}

	@Override 
	public List<Reservation> confirmQuotes(String name) throws Exception {
		
		List<Reservation> resList = new ArrayList<Reservation>();
		
		for(Quote quote : quotesMap.keySet()) {
			try {
				Reservation res = quotesMap.get(quote).confirmQuote(quote);
				resList.add(res);
			} catch(ReservationException e) {
				for(Reservation res : resList) {
					quotesMap.get(quote).cancelReservation(res);
				}
				throw new ReservationException("Couldn't confirm a quote");
			}
		}
		
		return resList;
		
	}

	@Override
	public List<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException {
		
		List<CarType> allAvailableCarTypes = new ArrayList<CarType>();
		
		List<ICarRentalCompany> allCompanies = (List<ICarRentalCompany>) getNamingService().getAllCompanies().values();
		for(ICarRentalCompany crc : allCompanies) {
			Set<CarType> available = crc.getAvailableCarTypes(start, end);
			allAvailableCarTypes.addAll(available);
			
		}
		
		return allAvailableCarTypes;
		
	}

	@Override
	public CarType getCheapestCarType(Date start, Date end, String region) {
		
		HashMap<CarType, Double> cheapestCarType = new HashMap<CarType, Double>();
		
		List<ICarRentalCompany> allCompanies = (List<ICarRentalCompany>) getNamingService().getAllCompanies().values();
		List<ICarRentalCompany> companiesToBeRemoved = new ArrayList<ICarRentalCompany>();
		
		for(ICarRentalCompany crc : allCompanies) {
			if(!crc.isLocatedInRegion(region)) {
				companiesToBeRemoved.add(crc);
			}
		}
		
		allCompanies.removeAll(companiesToBeRemoved);
		
		for(ICarRentalCompany crc : allCompanies) {
			CarType carType = crc.getCheapestCarType(start, end, region);
			
			cheapestCarType.put(carType, crc.getCheapestCarTypePrice(carType, start, end));
		}
		
		CarType cheapest = null;
		double minValue = 0;
		
		for(CarType ct : cheapestCarType.keySet()) {
			if(cheapestCarType.get(ct) > minValue) {
				minValue = cheapestCarType.get(ct);
				cheapest = ct;
			}
		}
		
		return cheapest;
		
	}
	

}
