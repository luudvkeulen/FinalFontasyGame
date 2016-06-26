package queryServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	String getAddress() throws RemoteException;
	
	int getPort() throws RemoteException;
	
	String getName() throws RemoteException;
	
	/**
	 * 
	 * @return adres + ":" + port
	 * @throws RemoteException 
	 */
	String getFullAddress() throws RemoteException;
	
	int getPlayers() throws RemoteException;
}
