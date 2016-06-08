package queryServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerList extends UnicastRemoteObject implements IServerList{
	public List<IServer> servers;
	
	public ServerList () throws  RemoteException {
		this.servers = new ArrayList();
	}
	
	@Override
	public List<IServer> getServers(){
		return this.servers;
	}
	
	@Override
	public void addServer(int players, String address, int port) throws RemoteException {
		if(serverExists(address, port)) return;
		servers.add(new Server(address, port, players));
	}
	
	@Override
	public void addServer(int players, String address, int port, String name) throws RemoteException {
		if(serverExists(address, port)) return;
		servers.add(new Server(address, port, players, name));
	}
	
	public boolean serverExists(String address, int port) throws RemoteException {
		for (IServer s : servers) {
			if(s.getAddress().equals(address) && s.getPort() == port) return true;
		}
		return false;
	}
	
	@Override
	public void removeServer(String address) throws RemoteException {
		for(IServer s : servers) {
			if(s.getFullAddress().equals(address)) {
				servers.remove(s);
			}
		}
	}
	
	public void clearServers() {
		this.servers.clear();
	}
}
