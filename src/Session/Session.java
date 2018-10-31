package Session;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import NamingService.INamingService;



public class Session implements ISession{

	private boolean Open;
	private String userName;
	
	private static INamingService namingService;
	
    /***************
     * CONSTRUCTOR 
     * @throws RemoteException *
     ***************/
    
	//private static Registry registry = LocateRegistry.getRegistry();
	Registry registry;
	
	public Session(String name) throws RemoteException{
		Open = true;
		userName = name;
		ISession stub = (ISession) UnicastRemoteObject.exportObject(this,0);
		registry = LocateRegistry.getRegistry();
		registry.rebind(userName, stub);
	}

	

	public void store() throws AccessException, RemoteException, NotBoundException {
		try{registry.unbind(userName);}
		catch(NotBoundException e){
			System.out.println("No session for this username");
		}
		Open = false;
	}

	public static void setNamingService(INamingService namings){
		namingService = namings;
	}

	public static INamingService getNamingService(){
		return namingService;
	}

	public void remove() throws Throwable {
		if(Open = true){
			try{registry.unbind(userName);}
			catch(NotBoundException e){
				System.out.println("No session for this username");
			}
		}
		this.finalize();
	}



	
	
	
}
