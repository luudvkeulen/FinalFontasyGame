package queryServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerList extends Remote {
	public List<IServer> getServers() throws RemoteException;
	
	public void addServer(String address, int port) throws RemoteException;
}
