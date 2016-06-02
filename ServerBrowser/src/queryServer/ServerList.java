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
	public void addServer(String address, int port) throws RemoteException {
		for (IServer s : servers) {
			if(s.getAddress().equals(address) && s.getPort() == port) return;
		}
		servers.add(new Server(address, port));
		System.out.println("added server:" + address + port);
		for(IServer server : servers) {
			System.out.println(server.getFullAddress());
		}
	}
	
	@Override
	public void removeServer(String address) throws RemoteException {
		for(IServer s : servers) {
			if(s.getFullAddress().equals(address)) {
				servers.remove(s);
			}
		}
	}
}
