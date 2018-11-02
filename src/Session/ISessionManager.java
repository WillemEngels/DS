package Session;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISessionManager extends Remote{

	public Session createReservationSession(String name) throws RemoteException, Exception;
	
	public void removeSession(String name) throws Throwable;
	
	public Session openSession(String name) throws RemoteException, Exception;

	public Session createManagerSession(String name, String rentalName) throws RemoteException, Exception;

	public void storeSession(String name) throws AccessException, RemoteException, NotBoundException;
}
