package NamingService;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NamingServiceServer {

	public static void main(String[] args) throws AccessException, RemoteException{
		NamingService namings = new NamingService();
		INamingService stub = (INamingService) UnicastRemoteObject.exportObject(namings,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("NamingService", stub);
		
		System.out.println("Naming Server running");
	}
}
