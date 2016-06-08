package queryServer;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Server implements IServer, Serializable {
	public String address;
	public int port;
	public String name;
	private int players;
	
	public Server(String address, int port, int players) {
		this.address = address;
		this.port = port;
		this.name = "Unnamed server";
		this.players = players;
	}
	
	public Server(String address, int port, int players, String name) {
		this.address = address;
		this.port = port;
		this.name = name;
		this.players = players;
	}

	@Override
	public String getAddress() throws RemoteException {
		return address;
	}

	@Override
	public int getPort() throws RemoteException {
		return port;
	}

	@Override
	public String getFullAddress() throws RemoteException {
		return this.address + ":" + this.port;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public int getPlayers() throws RemoteException {
		return players;
	}
}
