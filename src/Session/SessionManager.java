package Session;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionManager implements ISessionManager{

	private HashMap<String,Session> map = new HashMap();
	//private HashMap<String,ReservationSession> reservationMap = new HashMap();
	
	
	
	public SessionManager() throws AccessException, RemoteException{
		ISessionManager stub = (ISessionManager) UnicastRemoteObject.exportObject(this,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("SessionManager", stub);
	}
	
	
	@Override
	public ManagerSession createManagerSession(String name) throws RemoteException, Exception {
		if(map.containsKey(name)){
			Session session = map.get(name);
			if(session instanceof ManagerSession){
				return (ManagerSession) session;
			}
			else throw new Exception("Another session of other type exists");
		}
		else{
			ManagerSession session = new ManagerSession(name);
			map.put(name, session);
			return session;
		}
	}
	
	
	@Override
	public Session createReservationSession(String name) throws RemoteException, Exception {
		if(map.containsKey(name)){
			Session session = map.get(name);
			if(session instanceof ReservationSession){
				return (ReservationSession) session;
			}
			else throw new Exception("Another session of other type exists");
		}
		else{
			ManagerSession session = new ManagerSession(name);
			map.put(name, session);
			return session;
		}
	}
	@Override
	public void storeSession(String name) throws AccessException, RemoteException, NotBoundException {
		Session session = map.get(name);
		session.store();
		
	}
	
	public void removeSession(String name) throws Throwable{
		Session session = map.get(name);
		session.remove();
	}


	@Override
	/*
	 * (non-Javadoc)
	 * @see rental.ISessionManager#openSession(java.lang.String)
	 * @return session with this username, else creates a new RESERVATION session
	 */
	public Session openSession(String name) throws RemoteException, Exception {
		if(map.containsKey(name)) return map.get(name);
		else return createReservationSession(name);
	}




}
