package queryServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class QueryServer {
	private ServerList serverList;
	private Registry registry;
	
	static final int QUERYPORT = 420;
	static final String BINDINGNAME = "serverList";
	
	public QueryServer(){
		//Try creating the serverlist
		try {
			serverList = new ServerList();
			System.out.println("Succesfully created serverlist");
		} catch (RemoteException re) {
			System.out.println("queryServer: RemoteException: " + re.getMessage());
            serverList = null;
		}
		
		//Try setting up the registery
		try {
			registry = LocateRegistry.createRegistry(QUERYPORT);
			System.out.println("Succesfully created registry");
		} catch (RemoteException re) {
			System.out.println("queryServer: RemoteException: " + re.getMessage());
            registry = null;
		}
		
		//Try binding the server
		try {
			registry.rebind("serverList", serverList);
			System.out.println("Succesfully binded server list");
		} catch (RemoteException re) {
			System.out.println("queryServer: RemoteException: " + re.getMessage());
		}
	}
	public static void main(String[] args) {
		QueryServer qServer = new QueryServer();
	}
}
